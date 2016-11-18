package com.pin2.pedrobino.domain.request;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsRoute;
import com.pin2.pedrobino.domain.city.City;
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
