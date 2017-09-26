package com.turvo.acme.services.dto;

import java.util.Date;

public class ActiveVehicle {

    private long routeId;
    private long vehicleId;
    private long pointId;
    private Date createdOn;
    private long mappedPointId;
    private double latitude;
    private double longitude;

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public long getMappedPointId() {
        return mappedPointId;
    }

    public void setMappedPointId(long mappedPointId) {
        this.mappedPointId = mappedPointId;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
