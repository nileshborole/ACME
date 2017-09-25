package com.turvo.acme.services.geolocation;

import com.turvo.acme.entity.geolocation.GeoPoint;
import org.springframework.stereotype.Component;

@Component("vehicleTracker")
public class TrackVehicleDeviation implements Runnable {

    private static final String ACTIVE_VEHICLE_SELECT = "active_vehicle_detail_select";
    private static final String ACTIVE_VEHICLE_PREV_MAPPING_SELECT = "active_vehicle_prev_mapped_detail_select";

    private boolean isRunning;

    public TrackVehicleDeviation(){
        this.isRunning = false;
    }


    @Override
    public void run() {
        this.isRunning = true;
        while (isRunning){

        }
    }



    private double getHaversineDistance(GeoPoint point1, GeoPoint point2){

        return 0;
    }

    private void trackVehicles(){

    }


    public boolean isRunning() {
        return isRunning;
    }

    public void stop(){
        this.isRunning = false;
    }

}
