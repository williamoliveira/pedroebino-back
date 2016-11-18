package com.pin2.pedrobino.domain.request;

import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private ProposalValueCalculator proposalValueCalculator;

    public List<Proposal> generateProposals(Request request) {
        List<Proposal> proposals = new ArrayList<>();

        List<LocalDateTime> dates = new ArrayList<>();

        dates.add(request.getPreferredDate().minusDays(1));
        dates.add(request.getPreferredDate());
        dates.add(request.getPreferredDate().plusDays(1));

        dates.forEach(localDateTime -> {
            Proposal proposal = generateForDate(localDateTime, request);
            if (proposal != null) proposals.add(proposal);
        });

        return proposals;
    }

    private Proposal generateForDate(LocalDateTime dateStart, Request request) {
        LocalDateTime dateEnd = dateStart.plusSeconds(request.getEstimatedTravelDuration());

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
        proposal.setRequest(request);
        proposal.setValue(proposalValueCalculator.calculate(proposal));

        return proposal;
    }

    private Proposal findBestTruckAndDriversCombination(List<Truck> trucks,
                                                        List<Driver> drivers,
                                                        int driversCount) {
        // Current best combination
        Truck bestTruck = null;
        List<Driver> bestDrivers = new ArrayList<>();

        loop:
        {
            for (Truck truck : trucks) {
                bestTruck = truck;

                for (Driver driver : drivers) {
                    if (isLicenseCompatible(driver.getLicense(), truck.getLicense())) {
                        bestDrivers.add(driver);

                        if (bestDrivers.size() == driversCount) {
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
}
