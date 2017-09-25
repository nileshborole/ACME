package com.turvo.acme.services.geolocation;

import com.turvo.acme.entity.geolocation.GeoLocationQuery;
import com.turvo.acme.entity.geolocation.GeoPoint;
import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.entity.logistic.LogisticQuery;
import com.turvo.acme.entity.logistic.VehicleRoute;
import com.turvo.acme.persistence.registry.Repository;
import com.turvo.acme.services.dto.VehicleGPSInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/logistic/gpsListener")
public class VehicleGPSListenerService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleGPSListenerService.class);

    @Autowired
    private Repository repository;

    @PostMapping("/vehicle")
    @ResponseBody
    public boolean listenGPS(@RequestBody VehicleGPSInfo gpsInfoS) throws Exception{

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("vehicleId", gpsInfoS.getVehicleId());
            List result = repository.find(LogisticQuery.ACTIVE_VEHICLE_SELECT.query(), params);
            long routeId = 0;
            GeoPoint point = new GeoPoint();
            point.setLatitude(gpsInfoS.getLatitude());
            point.setLongitude(gpsInfoS.getLongitude());
            if (result == null || result.isEmpty()) {
                VehicleRoute vehicleRoute = new VehicleRoute();
                vehicleRoute.setVehicleId(gpsInfoS.getVehicleId());
                vehicleRoute.setStartTime(new Date());
                Route route = new Route();
                route.setName("vehicle-route-" + gpsInfoS.getVehicleId() + "-" + System.currentTimeMillis());
                route.setTypeId(3); //vehicle route type
                vehicleRoute.setRoute(route);
                vehicleRoute = (VehicleRoute) repository.create(vehicleRoute);
                routeId = vehicleRoute.getRoute().getId();
            } else {
                routeId = (long) ((Map<String, Object>) result.get(0)).get("ROUTE_ID");
            }

            point = (GeoPoint) repository.create(point);
            params.clear();
            params.put("routeId", routeId);
            params.put("geoPointId", point.getId());
            params.put("createdOn", new Date());
            repository.fireAdhoc(GeoLocationQuery.GEOPOINT_ROUTE_MAPPING_INSERT.query(), params);
        }catch (Exception e){
            logger.error("", e);
            return false;
        }

        return true;
    }




}
