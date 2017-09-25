package com.turvo.acme.entity.listener;

import com.turvo.acme.entity.geolocation.GeoLocationQuery;
import com.turvo.acme.entity.geolocation.GeoPoint;
import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.listener.BaseListener;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("routeListener")
public class RouteListener extends BaseListener {

    @Autowired
    private Repository repository;

    @Override
    public ACMEEntity postCreate(ACMEEntity entity, Entity meta) throws Exception {
        addRoutePoint(((Route) entity).getPoints(), entity.getId());
        return super.postCreate(entity, meta);
    }

    @Override
    public ACMEEntity postUpdate(ACMEEntity entity, Entity meta) throws Exception {
        removeRoutePoint(entity.getId());
        addRoutePoint(((Route) entity).getPoints(), entity.getId());
        return super.postUpdate(entity, meta);
    }

    @Override
    public ACMEEntity postRestore(ACMEEntity entity, Entity meta) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("routeId", entity.getId());
        ((Route) entity).setPoints(repository.find(GeoLocationQuery.GEOPOINT_BY_ROUTE_SELECT.query(), params));
        return super.postRestore(entity, meta);
    }

    @Override
    public void postDelete(long id, Entity meta) throws Exception {
        removeRoutePoint(id);
        super.postDelete(id, meta);
    }

    private void addRoutePoint(List<GeoPoint> points, long routeId) throws Exception{
        if(points != null){
            Map<String, Object> params = new HashMap<>();;
            for(GeoPoint point : points){
                point = (GeoPoint) repository.create(point);
                params.clear();
                params.put("routeId", routeId);
                params.put("geoPointId", point.getId());
                params.put("createdOn", new Date());
                repository.fireAdhoc(GeoLocationQuery.GEOPOINT_ROUTE_MAPPING_INSERT.query(), params);
            }
        }
    }

    private void removeRoutePoint(long routeId) throws Exception{
        Map<String, Object> params = new HashMap<>();
        params.put("routeId", routeId);
        repository.fireAdhoc(GeoLocationQuery.REMOVE_GEOPOINT_BY_ID.query(), params);
    }
}
