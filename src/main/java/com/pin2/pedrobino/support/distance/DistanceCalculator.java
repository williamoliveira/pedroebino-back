package com.pin2.pedrobino.support.distance;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrixElement;
import com.pin2.pedrobino.entities.city.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculator {

    @Autowired
    private GeoApiContext geoApiContext;

    public DistanceMatrixElement calculate(City from, City to) throws Exception {

        String[] origins = new String[1];
        origins[0] = from.getName() + " - " + from.getState().getName();

        String[] destinations = new String[1];
        destinations[0] = to.getName() + " - " + to.getState().getName();

        DistanceMatrixApiRequest distanceMatrixApiRequest
                = DistanceMatrixApi.getDistanceMatrix(geoApiContext, origins, destinations);

        return distanceMatrixApiRequest.await().rows[0].elements[0];
    }

}
