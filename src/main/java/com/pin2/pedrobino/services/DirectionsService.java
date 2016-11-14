package com.pin2.pedrobino.services;

import com.google.maps.*;
import com.google.maps.model.DirectionsRoute;
import com.pin2.pedrobino.entities.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectionsService {

    @Autowired
    private GeoApiContext geoApiContext;

    public DirectionsRoute getDirections(City from, City to) throws Exception {
        String origin = from.getName() + " - " + from.getState().getName();
        String destination = to.getName() + " - " + to.getState().getName();

        DirectionsApiRequest directionsApiRequest =
                DirectionsApi.getDirections(geoApiContext, origin, destination);

        return directionsApiRequest.await().routes[0];
    }

}
