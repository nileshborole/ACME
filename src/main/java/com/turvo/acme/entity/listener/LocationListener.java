package com.turvo.acme.entity.listener;

import com.turvo.acme.entity.geolocation.GeoLocationQuery;
import com.turvo.acme.entity.geolocation.GeoPoint;
import com.turvo.acme.entity.geolocation.Location;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.listener.BaseListener;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("locationListener")
public class LocationListener extends BaseListener{

    private static final Logger logger = LoggerFactory.getLogger(LocationListener.class);
    @Autowired
    private Repository repository;

    @Override
    public ACMEEntity preCreate(ACMEEntity entity, Entity meta) throws Exception {
        Location location = (Location) entity;
        if(location.getPoint()!= null)
            repository.create(location.getPoint());
        return super.preCreate(entity, meta);
    }

    @Override
    public ACMEEntity postUpdate(ACMEEntity entity, Entity meta) throws Exception {
        GeoPoint point = ((Location) entity).getPoint();
        if(point != null)
            repository.update(point);
        return super.postUpdate(entity, meta);
    }

    @Override
    public void delete(long id, Entity meta) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("locationId", id);
        List result = repository.find(GeoLocationQuery.GEOPOINT_LOCATION_COUNT.query(), params);

        if(result == null || result.isEmpty()){
            logger.debug("Deleting geo point for location : "+ id);
            repository.fireAdhoc(GeoLocationQuery.REMOVE_GEOPOINT_BY_LOCATION.query(), params);
        }

        super.delete(id, meta);
    }
}
