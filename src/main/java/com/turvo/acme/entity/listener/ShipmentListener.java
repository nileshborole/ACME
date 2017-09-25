package com.turvo.acme.entity.listener;

import com.turvo.acme.entity.geolocation.Location;
import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.entity.logistic.Shipment;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.listener.BaseListener;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shipmentListener")
public class ShipmentListener extends BaseListener {

    @Autowired
    private Repository repository;


    @Override
    public ACMEEntity preCreate(ACMEEntity entity, Entity meta) throws Exception {

        Shipment shipment = (Shipment) entity;
        addRoute(shipment.getPredefinedRoute());

        createLocation(shipment.getStartLocation());
        createLocation(shipment.getEndLocation());

        return super.preCreate(entity, meta);
    }

    @Override
    public ACMEEntity preUpdate(ACMEEntity entity, Entity meta) throws Exception {

        Shipment shipment = (Shipment) entity;
        Shipment oldShipment = repository.restore(entity.getId(), Shipment.class);

        deleteRoute(oldShipment.getPredefinedRoute());
        addRoute(shipment.getPredefinedRoute());

        createLocation(shipment.getStartLocation());
        createLocation(shipment.getEndLocation());

        return super.preUpdate(entity, meta);
    }

    @Override
    public ACMEEntity postRestore(ACMEEntity entity, Entity meta) throws Exception {
        Shipment shipment = (Shipment) entity;
        shipment.setStartLocation(getLocation(shipment.getStartLocation()));
        shipment.setEndLocation(getLocation(shipment.getEndLocation()));
        return super.postRestore(entity, meta);
    }

    @Override
    public void delete(long id, Entity meta) throws Exception {
        Shipment shipment = repository.restore(id, Shipment.class);
        deleteRoute(shipment.getPredefinedRoute());
        super.delete(id, meta);
    }

    @Override
    public void postDelete(long id, Entity meta) throws Exception {
        super.postDelete(id, meta);
    }

    private void createLocation(Location location) throws Exception{
        if(location != null && location.getId() < 1){
            repository.create(location);
        }
    }

    private Location getLocation(Location location) throws Exception{
        if(location != null && location.getId() > 0 && location.getPoint() == null){
            location = repository.restore(location.getId(), Location.class);
        }
        return location;
    }

    private void deleteRoute(Route route) throws Exception{
        if(route != null){
            repository.delete(route.getId(), Route.class);
        }
    }

    private void addRoute(Route route) throws Exception{
        if(route != null){
            repository.create(route);
        }
    }

}
