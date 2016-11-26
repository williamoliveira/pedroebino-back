package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.pin2.pedrobino.domain.request.Request;
import com.pin2.pedrobino.domain.request.RequestsRepository;
import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProposalsGenerator {

    @Autowired
    private RequestsRepository requestsRepository;

    @Autowired
    private DriversRepository driversRepository;

    @Autowired
    private TrucksRepository trucksRepository;

    @Autowired
    private ProposalsRepository proposalsRepository;

    @Autowired
    private ProposalValueCalculator proposalValueCalculator;

    public List<Proposal> generateProposals(Request request) {

        if (!request.isCanShare()) {
            return generateNewProposals(request);
        }

        List<Proposal> newProposals = generateNewProposals(request);
        List<Proposal> shareableProposals = findShareableProposals(request);


        List<Proposal> mergedProposals = new ArrayList<>();
        mergedProposals.addAll(newProposals);
        mergedProposals.addAll(shareableProposals);

        List<Proposal> proposals = new ArrayList<>();

        generateDatesBasedOnRequest(request).forEach(date -> {
            Proposal proposal = getBestProposalFromArrayForDate(mergedProposals, date);

            if (proposal != null) proposals.add(proposal);
        });


        return proposals;
    }

    private Proposal getBestProposalFromArrayForDate(List<Proposal> proposals, Date date){
        Proposal bestProposal = null;

        for(Proposal proposal : proposals){

            if(proposal.getLeavesAt().getTime() == date.getTime()){
                if(bestProposal == null || proposal.getValue() < bestProposal.getValue()){
                    bestProposal = proposal;
                }
            }
        }

        return bestProposal;
    }

    private List<Proposal> generateNewProposals(Request request) {
        List<Proposal> proposals = new ArrayList<>();

        generateDatesBasedOnRequest(request).forEach(date -> {
            Proposal proposal = generateProposalForDate(date, request);

            if (proposal != null) proposals.add(proposal);
        });

        return proposals;
    }

    private List<Proposal> findShareableProposals(Request request) {
        List<Proposal> proposals = new ArrayList<>();

        generateDatesBasedOnRequest(request).forEach(date -> {
            Proposal proposal = findShareableProposalForDate(date, request);

            if (proposal != null) proposals.add(proposal);
        });

        return proposals;
    }

    private List<Date> generateDatesBasedOnRequest(Request request){
        List<Date> dates = new ArrayList<>();

        dates.add(minusDays(request.getPreferredDate(), 1));
        dates.add(request.getPreferredDate());
        dates.add(plusDays(request.getPreferredDate(), 1));


        return dates;
    }

    private Proposal findShareableProposalForDate(Date dateStart, Request request) {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                dateStart,
                request.getFrom(),
                request.getTo(),
                request.getVolume()
        );

        if (proposal != null) {
            proposal.setValue(proposalValueCalculator.calculate(proposal));
        }

        return proposal;
    }

    private Proposal generateProposalForDate(Date dateStart, Request request) {
        Date dateEnd = plusSeconds(dateStart, request.getEstimatedTravelDuration());

        List<Truck> availableTrucks = trucksRepository.findAvailableTrucks(
                dateStart,
                dateEnd,
                request.getVolume()
        );

        List<Driver> availableDrivers = driversRepository.findAvailableDrivers(
                dateStart,
                dateEnd,
                request.getFrom(),
                request.getDistance()
        );


        boolean needsTwoDrivers = request.getEstimatedTravelDurationInHours() > 8.0;
        int driversCount = needsTwoDrivers ? 2 : 1;

        Proposal proposal = findBestTruckAndDriversCombination(
                availableTrucks,
                availableDrivers,
                driversCount
        );

        if (proposal == null) {
            return null;
        }

        proposal.setLeavesAt(dateStart);
        proposal.setArrivesAt(dateEnd);
        proposal.addRequest(request);
        proposal.setValue(proposalValueCalculator.calculate(proposal));

        return proposal;
    }


    private Proposal findBestTruckAndDriversCombination(List<Truck> trucks,
                                                        List<Driver> drivers,
                                                        int driversCount) {
        // Current best combination
        Truck bestTruck = null;
        List<Driver> bestDrivers = new ArrayList<>();

        loop: {
            for (Truck truck : trucks) {
                bestTruck = truck;

                for (Driver driver : drivers) {
                    if (isLicenseCompatible(driver.getLicense(), truck.getLicense())) {
                        bestDrivers.add(driver);

                        if (bestDrivers.size() >= driversCount) {
                            break loop;
                        }
                    }
                }

                // Could not find <driversCount> best drivers for the truck, lets clear the array
                bestDrivers.clear();
            }
        }

        if (bestTruck == null || bestDrivers.size() < driversCount) {
            return null;
        }

        Proposal proposal = new Proposal();

        proposal.setTruck(bestTruck);
        proposal.setDrivers(drivers);

        return proposal;
    }

    private boolean isLicenseCompatible(String driverLicense, String vehicleLicense) {
        return driverLicense.compareToIgnoreCase(vehicleLicense) > -1;
    }

    private Date plusSeconds(Date date, long seconds){
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ldt = ldt.plusSeconds(seconds);

        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date plusDays(Date date, long days){
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ldt = ldt.plusDays(days);

        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date minusDays(Date date, long days){
        LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ldt = ldt.minusDays(days);

        return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
    }
}
