package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.request.Request;
import com.pin2.pedrobino.support.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProposalsCalculator {

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Autowired
    private ProposalValueCalculator proposalValueCalculator;

    @Autowired
    private NewProposalsGenerator newProposalsGenerator;

    public List<Proposal> calculateProposals(Request request) {
        return calculateProposals(
                request.getPreferredDate(),
                request.isCanShare(),
                request.getFrom(),
                request.getTo(),
                request.getVolume(),
                request.getEstimatedTravelDuration(),
                request.getDistance()
        );
    }

    public List<Proposal> calculateProposals(Date preferredDate,
                                             boolean canShare,
                                             City from,
                                             City to,
                                             int volume,
                                             long travelDuration,
                                             long distance) {

        if (!canShare) {
            return newProposalsGenerator.generateNewProposals(
                    preferredDate,
                    canShare,
                    from,
                    to,
                    volume,
                    travelDuration,
                    distance
            );
        }

        List<Proposal> newProposals = newProposalsGenerator.generateNewProposals(
                preferredDate,
                canShare,
                from,
                to,
                volume,
                travelDuration,
                distance
        );

        List<Proposal> shareableProposals = findShareableProposals(
                preferredDate,
                canShare,
                from,
                to,
                volume,
                travelDuration,
                distance
        );


        List<Proposal> mergedProposals = new ArrayList<>();
        mergedProposals.addAll(newProposals);
        mergedProposals.addAll(shareableProposals);

        List<Proposal> proposals = new ArrayList<>();

        getDatesAroundPreferredDate(preferredDate).forEach(date -> {
            Proposal proposal = getBestProposalFromArrayForDate(mergedProposals, date);

            if (proposal != null) proposals.add(proposal);
        });


        return proposals;
    }

    private Proposal getBestProposalFromArrayForDate(List<Proposal> proposals, Date date) {
        Proposal bestProposal = null;

        for (Proposal proposal : proposals) {

            if (proposal.getLeavesAt().getTime() == date.getTime()) {
                if (bestProposal == null || proposal.getValue() < bestProposal.getValue()) {
                    bestProposal = proposal;
                }
            }
        }

        return bestProposal;
    }

    private List<Proposal> findShareableProposals(Date preferredDate,
                                                  boolean canShare,
                                                  City from,
                                                  City to,
                                                  int volume,
                                                  long travelDuration,
                                                  long distance) {
        List<Proposal> proposals = new ArrayList<>();

        getDatesAroundPreferredDate(preferredDate).forEach(dateStart -> {
            Proposal proposal = findShareableProposalForDate(
                    dateStart,
                    canShare,
                    from,
                    to,
                    volume,
                    travelDuration,
                    distance
            );

            if (proposal != null) proposals.add(proposal);
        });

        return proposals;
    }

    private List<Date> getDatesAroundPreferredDate(Date preferredDate) {
        List<Date> dates = new ArrayList<>();

        dates.add(DateUtil.minusDays(preferredDate, 1));
        dates.add(preferredDate);
        dates.add(DateUtil.plusDays(preferredDate, 1));


        return dates;
    }

    private Proposal findShareableProposalForDate(Date dateStart,
                                                  boolean canShare,
                                                  City from,
                                                  City to,
                                                  int volume,
                                                  long travelDuration,
                                                  long distance) {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                dateStart,
                from,
                to,
                volume
        );

        if (proposal != null) {
            proposal.setValue(proposalValueCalculator.calculate(
                    proposal.getDrivers(),
                    proposal.getTruck(),
                    travelDuration,
                    distance,
                    canShare
            ));
        }

        return proposal;
    }


}
