package com.turvo.acme.entity.listener;

import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.entity.logistic.VehicleRoute;
import com.turvo.acme.persistence.entity.ACMEEntity;
import com.turvo.acme.persistence.listener.BaseListener;
import com.turvo.acme.persistence.registry.Entity;
import com.turvo.acme.persistence.registry.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("vehicleRouteListener")
public class VehicleRouteListener extends BaseListener {

    @Autowired
    private Repository repository;


    @Override
    public ACMEEntity preCreate(ACMEEntity entity, Entity meta) throws Exception {
        VehicleRoute vehicleRoute = (VehicleRoute) entity;
        addRoute(vehicleRoute.getRoute());
        return super.preCreate(entity, meta);
    }

    @Override
    public ACMEEntity preUpdate(ACMEEntity entity, Entity meta) throws Exception {
        VehicleRoute vehicleRoute = (VehicleRoute) entity;
        VehicleRoute oldVehicleRoute = repository.restore(entity.getId(), VehicleRoute.class);

        deleteRoute(oldVehicleRoute.getRoute());
        addRoute(vehicleRoute.getRoute());

        return super.preUpdate(entity, meta);
    }

    @Override
    public ACMEEntity postRestore(ACMEEntity entity, Entity meta) throws Exception {
        VehicleRoute vehicleRoute = (VehicleRoute) entity;

        return super.postRestore(entity, meta);
    }

    @Override
    public void delete(long id, Entity meta) throws Exception {
        VehicleRoute vehicleRoute = repository.restore(id, VehicleRoute.class);
        deleteRoute(vehicleRoute.getRoute());
        super.delete(id, meta);
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
