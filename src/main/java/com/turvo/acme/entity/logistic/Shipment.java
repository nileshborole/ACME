package com.turvo.acme.entity.logistic;

import com.turvo.acme.entity.geolocation.Location;
import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.persistence.entity.StandardEntity;
import com.turvo.acme.persistence.registry.Entity;

import java.util.Date;

@Entity(queryPrefix = "shpmt", module = "logistic", listener = "shipmentListener")
public class Shipment extends StandardEntity {

    private Location startLocation;
    private Location endLocation;
    private Date startTime;
    private Date endTime;
    private Route predefinedRoute;
    private long vehicleRouteId;

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Route getPredefinedRoute() {
        return predefinedRoute;
    }

    public void setPredefinedRoute(Route predefinedRoute) {
        this.predefinedRoute = predefinedRoute;
    }

    public long getVehicleRouteId() {
        return vehicleRouteId;
    }

    public void setVehicleRouteId(long vehicleRouteId) {
        this.vehicleRouteId = vehicleRouteId;
    }
}
