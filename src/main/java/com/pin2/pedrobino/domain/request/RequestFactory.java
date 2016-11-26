package com.pin2.pedrobino.domain.request;

import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.pin2.pedrobino.domain.proposal.Proposal;
import com.pin2.pedrobino.domain.proposal.ProposalsGenerator;
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

        request.setStatus(RequestStatus.PENDING);

        try {
            DirectionsRoute directionsRoute = distanceCalculator.getDirections(
                    request.getFrom(),
                    request.getTo()
            );

            DirectionsLeg leg = directionsRoute.legs[0];

            request.setEstimatedTravelDuration(leg.duration.inSeconds);
            request.setDistance(leg.distance.inMeters);

            request.setOverviewPolyline(directionsRoute.overviewPolyline.getEncodedPath());

        } catch (Exception e) {
            e.printStackTrace();

            // TODO for testing
            request.setEstimatedTravelDuration(5);
            request.setDistance(500000);
        }

        List<Proposal> proposals = proposalsGenerator.generateProposals(request);

        if (proposals.size() > 0) {
            request.setProposals(proposals);
        }


        return request;
    }

}
