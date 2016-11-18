package com.pin2.pedrobino.domain.request;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestFactory {

    @Autowired
    private DirectionsService distanceCalculator;

    @Autowired
    private ProposalsGenerator proposalsGenerator;

    public Request create(Request request) {
        try {

            request.setStatus(RequestStatus.PENDING);

            DirectionsRoute directionsRoute = distanceCalculator.getDirections(
                    request.getFrom(),
                    request.getTo()
            );

            DirectionsLeg leg = directionsRoute.legs[0];

            request.setEstimatedTravelDuration(leg.duration.inSeconds);
            request.setDistance(leg.distance.inMeters);

            request.setOverviewPolyline(directionsRoute.overviewPolyline.getEncodedPath());

            List<Proposal> proposals = proposalsGenerator.generateProposals(request);

            if (proposals.size() > 0) {
                request.setProposals(proposals);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }

}
