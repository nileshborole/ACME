package com.turvo.acme.services.shipment;

import com.google.maps.*;
import com.google.maps.model.*;
import com.turvo.acme.entity.geolocation.GeoPoint;
import com.turvo.acme.entity.geolocation.Location;
import com.turvo.acme.entity.geolocation.Route;
import com.turvo.acme.entity.logistic.Shipment;
import com.turvo.acme.persistence.registry.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("logistic/shipment")
public class ShipmentService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentService.class);

    @Autowired
    GeoApiContext context;

    @Autowired
    private Repository repository;

    @GetMapping("/new")
    public boolean createShipment(@RequestParam(value = "source", required = true) String source,
                                  @RequestParam(value = "destination", required = true) String destination) throws Exception{

        DirectionsApiRequest request =  DirectionsApi.getDirections(context, source, destination);
        DirectionsRoute[] routes =  request.await();

        Shipment shipment = new Shipment();
        Location startLocation = getLocation(source);
        Location endLocation = getLocation(destination);
        shipment.setStartLocation(startLocation);
        shipment.setEndLocation(endLocation);
        Route predefinedRoute = new Route();
        predefinedRoute.setName("Shipment-"+source+"-"+destination);
        predefinedRoute.setTypeId(4);
        if(routes != null && routes.length > 0){

            DirectionsRoute route = routes[0];
            for(DirectionsLeg leg : route.legs){
                for(DirectionsStep step : leg.steps){
                    step.polyline.decodePath().stream().forEach(p -> {
                        GeoPoint point = new GeoPoint();
                        point.setLatitude(p.lat);
                        point.setLongitude(p.lng);
                        predefinedRoute.addPoint(point);
                    });
                }
            }

        }

        shipment.setPredefinedRoute(predefinedRoute);

        repository.create(shipment);

        return true;
    }

    private Location getLocation(String location) throws Exception{

        GeocodingApiRequest geoRequest = GeocodingApi.geocode(context, location);
        GeocodingResult result = geoRequest.await()[0];
        Location startLcation = new Location();
        startLcation.setName(location);
        startLcation.setDescription(location);
        startLcation.setPoint(new GeoPoint());
        startLcation.getPoint().setLatitude(result.geometry.location.lat);
        startLcation.getPoint().setLongitude(result.geometry.location.lng);
        return startLcation;
    }


}
