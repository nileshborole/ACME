package com.turvo.acme.entity.geolocation;

import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.registry.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity(queryPrefix = "route", module = "geolocation", listener = "routeListener")
public class Route implements ACMEEntity {

    private long id;
    private String name;
    private long typeId;
    private List<GeoPoint> points;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public List<GeoPoint> getPoints() {
        return points;
    }

    public void setPoints(List<GeoPoint> points) {
        this.points = points;
    }

    public void addPoint(GeoPoint point){
        if(points == null)
            points = new ArrayList<>();

        points.add(point);
    }
}
