package com.turvo.acme.entity.geolocation;

import com.turvo.acme.persistence.entity.StandardEntity;
import com.turvo.acme.persistence.registry.Entity;

@Entity(queryPrefix = "locat", module = "geolocation", listener = "locationListener")
public class Location extends StandardEntity {

    private String name;
    private String description;
    private GeoPoint point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GeoPoint getPoint() {
        return point;
    }

    public void setPoint(GeoPoint point) {
        this.point = point;
    }
}
