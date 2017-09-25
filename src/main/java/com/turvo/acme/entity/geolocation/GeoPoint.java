package com.turvo.acme.entity.geolocation;

import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Entity;

@Entity(queryPrefix = "geopt", module = "geolocation")
public class GeoPoint implements ACMEEntity{

    private long id;
    private double latitude;
    private double longitude;
    private long pointId = -1;


    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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

    public long getPointId() {
        return pointId;
    }

    public void setPointId(long pointId) {
        this.pointId = pointId;
    }
}
