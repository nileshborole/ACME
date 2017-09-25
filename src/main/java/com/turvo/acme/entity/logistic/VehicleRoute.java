package com.turvo.acme.entity.logistic;

import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.persistence.entity.StandardEntity;
import com.turvo.acme.persistence.registry.Entity;

import java.util.Date;

@Entity(queryPrefix = "vclrt", module = "logistic", listener = "vehicleRouteListener")
public class VehicleRoute extends StandardEntity {

    private long vehicleId;
    private Date startTime;
    private Date endTime;
    private Route route;

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
