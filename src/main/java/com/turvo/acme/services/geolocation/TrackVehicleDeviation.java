package com.turvo.acme.services.geolocation;

import com.turvo.acme.entity.geolocation.GeoPoint;
import com.turvo.acme.entity.notif.Notification;
import com.turvo.acme.exception.ACMEException;
import com.turvo.acme.persistence.registry.Repository;
import com.turvo.acme.services.dto.ActiveShipment;
import com.turvo.acme.services.dto.ActiveVehicle;
import com.turvo.acme.services.dto.DeviatedShipment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("vehicleTracker")
public class TrackVehicleDeviation implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(TrackVehicleDeviation.class);

    private static final String ACTIVE_VEHICLE_SELECT = "active_vehicle_detail_select";
    private static final String ACTIVE_VEHICLE_PREV_MAPPING_SELECT = "active_vehicle_prev_mapped_detail_select";
    private static final String ACTIVE_SHIPMENT_SELECT = "active_shipment_select";
    private static final String SHIPMENT_DEVIATION_SELECT = "deviated_active_shipement_select";
    private static final String SHIPMENT_DEVIATION_INSERT = "deviation_shipment_insert";
    private static final String SHIPMENT_DEVIATION_STATUS_UPDATE = "deviation_shipment_status_update";
    private static final String GEOPOINT_MAPPING_UPDATE = "geopoint_mapping_update";
    private static final String SHIPMENT_PREDEFINED_ROUTE_POINT_SELECT = "geopt_select";



    @Value("#{systemProperties['acme.vehicle.deviation.thread.sleep'] ?: 2000}")
    private String SLEEP_TIME_IN_MILLI_PROPERTY;

    @Value("#{systemProperties['acme.vehicle.deviation.thread.failOnInterrupt'] ?: TRUE}")
    private String failOnInterruption;

    @Value("#{systemProperties['acme.vehicle.deviation.threshold.km'] ?: 5}")
    private String deviationThreasholdProperty;

    private double deviationThreashold = -1;

    private long sleepTimeInMilli = -1;

    private static final int EARTH_RADIUS_IN_KM = 6371;



    @Autowired
    private Repository repository;

    private boolean isRunning;

    public TrackVehicleDeviation(){
        this.isRunning = false;
    }


    @Override
    public void run() {
        this.isRunning = true;
        while (isRunning){
            try {
                trackVehicles();
                Thread.sleep(getSleepTimeInMilli());
            }catch (InterruptedException e){
                logger.error("Vehicle Tracker Thread Interrupted.", e);
                if("TRUE".equalsIgnoreCase(failOnInterruption))
                    stop();
            }catch (Exception e){
                logger.error("Error in Vehicle Tracker Thread.",e);
                stop();
            }
        }
    }

    private void trackVehicles() throws Exception{

            Map<Long, ActiveShipment> activeShipments = getActiveShipmentDetails();
            Map<Long,ActiveVehicle> activeVehicles = getActiveVehicles();
            Map<Long,ActiveVehicle> prevMappedActiveVehicles = getPrevMappedActiveVehicles();
            Map<Long, DeviatedShipment> deviatedShipments = getDevialtedShipments();
            boolean isAlerted = false;
            boolean isDeviated = false;
            ActiveVehicle currentLocation = null;
            ActiveVehicle prevLocation = null;
            for(ActiveShipment shipment : activeShipments.values()){

                isDeviated = deviatedShipments.containsKey(shipment.getShipmentId());
                isAlerted = isDeviated;
                double deviation = 0;
                GeoPoint deviationPoint = null;
                if(!isDeviated){
                    currentLocation = activeVehicles.get(shipment.getVehicleId());
                    prevLocation = prevMappedActiveVehicles.get(shipment.getVehicleId());

                    List<GeoPoint> predefinedRoutePoints = getPredefinedRoutePoints(shipment.getShipmentId(),
                                                            prevLocation!=null? prevLocation.getMappedPointId() : 0);

                    deviation = getVehicleDeviation(currentLocation, predefinedRoutePoints, deviationPoint);

                    isDeviated = deviation == Double.MAX_VALUE || deviation >= getDeviationThreashold();

                }

                if(isDeviated && !isAlerted) {
                    pushAlert(shipment, deviationPoint, deviation, currentLocation);
                    recordShipmentDeviation(shipment, deviationPoint, deviation, currentLocation);
                }else if(!isDeviated){
                    if(isAlerted)
                        updateShipmentDeviation(shipment);
                    updateMappingPoint(currentLocation, deviationPoint);
                }
            }
    }

    private void updateMappingPoint(ActiveVehicle currentLocation, GeoPoint mappedPoint) throws ACMEException {

        Map<String, Object> params = new HashMap<>();
        params.put("id", currentLocation.getPointId());
        params.put("pointId", mappedPoint.getId());
        repository.fireAdhoc(GEOPOINT_MAPPING_UPDATE, params);
    }


    private void pushAlert(ActiveShipment shipment, GeoPoint deviatedFrom, double deviationDistance,
                           ActiveVehicle deviatedTo){
        Notification notification = new Notification();
        notification.setName("Shipment Diverted Alert");
        notification.setNotificationType(5);
        notification.setNotifMethTypeId(6);
        notification.setStatus(8);
        notification.setCreatedOn(new Date());

        notification.addParam("shipmentId", String.valueOf(shipment.getShipmentId()));
        notification.addParam("vehicleId", String.valueOf(shipment.getVehicleId()));
        notification.addParam("deviationDistance", String.valueOf(deviationDistance));

        try {
            repository.create(notification);
        } catch (ACMEException e) {
            logger.error("Failed to push deviation alerts!!", e);
        }
    }

    private void recordShipmentDeviation(ActiveShipment shipment, GeoPoint deviatedFrom, double deviationDistance,
                                         ActiveVehicle deviatedTo) throws ACMEException {

        Map<String, Object> params = new HashMap<>();
        params.put("shipmentId", shipment.getShipmentId() );
        params.put("vehicleId", shipment.getVehicleId());
        params.put("deviationPointId", deviatedTo.getPointId());
        params.put("deviatedFromPointId", deviatedFrom.getId());
        params.put("deviationTime", deviatedTo.getCreatedOn());

        repository.fireAdhoc(SHIPMENT_DEVIATION_INSERT, params);
    }

    private void updateShipmentDeviation(ActiveShipment shipment) throws ACMEException {
        Map<String, Object> params = new HashMap<>();
        params.put("shipmentId", shipment.getShipmentId() );
        params.put("vehicleId", shipment.getVehicleId());
        repository.fireAdhoc(SHIPMENT_DEVIATION_STATUS_UPDATE, params);
    }

    private double getVehicleDeviation(ActiveVehicle currentLocation, List<GeoPoint> pathPoints, GeoPoint deviationPoint){
        double minDeviation = Double.MAX_VALUE;
        for(GeoPoint point : pathPoints){
            double deviation = getHaversineDistanceInKM(currentLocation.getLatitude(), currentLocation.getLongitude(),
                                                            point.getLatitude(), point.getLongitude());

            if(deviation < minDeviation) {
                minDeviation = deviation;
                deviationPoint = point;
            }

        }

        return minDeviation;
    }



    private List<GeoPoint> getPredefinedRoutePoints(long shipmentRouteId, long minPointId) throws ACMEException {
        Map<String, Object> params = new HashMap<>();
        params.put("routeId", shipmentRouteId);
        if(minPointId > 0)
            params.put("minId", minPointId);
        return repository.find(SHIPMENT_PREDEFINED_ROUTE_POINT_SELECT, params, GeoPoint.class);
    }

    private Map<Long, DeviatedShipment> getDevialtedShipments() throws ACMEException{
        List<DeviatedShipment> deviatedShipments = repository.find(SHIPMENT_DEVIATION_SELECT, null);
        return deviatedShipments.stream().collect(Collectors.toMap(DeviatedShipment::getShipmentId, d -> d));
    }

    private Map<Long,ActiveShipment> getActiveShipmentDetails() throws ACMEException {
        List<ActiveShipment> activeShipments = repository.find(ACTIVE_SHIPMENT_SELECT, new HashMap<>());
        return activeShipments.stream().collect(Collectors.toMap(ActiveShipment::getVehicleId, s -> s));
    }

    private Map<Long,ActiveVehicle> getActiveVehicles() throws ACMEException {
        List<ActiveVehicle> activeVehicles = repository.find(ACTIVE_VEHICLE_SELECT, new HashMap<>());
        return activeVehicles.stream().collect(Collectors.toMap(ActiveVehicle::getVehicleId, s -> s));
    }

    public Map<Long,ActiveVehicle> getPrevMappedActiveVehicles() throws ACMEException {
        List<ActiveVehicle> activeVehicles = repository.find(ACTIVE_VEHICLE_PREV_MAPPING_SELECT, null);
        return activeVehicles.stream().collect(Collectors.toMap(ActiveVehicle::getVehicleId, s -> s));
    }




    private double getHaversineDistanceInKM(GeoPoint point1, GeoPoint point2){
        return getHaversineDistanceInKM(point1.getLatitude(), point1.getLongitude(), point2.getLatitude(), point2.getLongitude());
    }

    private double getHaversineDistanceInKM(double latitude1, double longitude1, double latitude2, double longitude2){
        double latitudeDiff = Math.toRadians(latitude2-latitude1);
        double longitudeDiff = Math.toRadians(longitude2 - longitude1);

        double fromLat = Math.toRadians(latitude1);
        double toLat = Math.toRadians(latitude2);

        double a = haversin(latitudeDiff) + Math.cos(fromLat) * Math.cos(toLat) * haversin(longitudeDiff);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return EARTH_RADIUS_IN_KM * c;
    }

    private double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }




    public boolean isRunning() {
        return isRunning;
    }

    public void stop(){
        this.isRunning = false;
    }


    private long getSleepTimeInMilli(){
        if(sleepTimeInMilli < 0){
            sleepTimeInMilli = SLEEP_TIME_IN_MILLI_PROPERTY != null && !SLEEP_TIME_IN_MILLI_PROPERTY.isEmpty()?
                                    Long.parseLong(SLEEP_TIME_IN_MILLI_PROPERTY) : 2000;
        }
        return sleepTimeInMilli;
    }

    private double getDeviationThreashold(){

        if(deviationThreashold < 0){
            deviationThreashold = Double.parseDouble(deviationThreasholdProperty);
        }
        return  deviationThreashold;
    }

}
