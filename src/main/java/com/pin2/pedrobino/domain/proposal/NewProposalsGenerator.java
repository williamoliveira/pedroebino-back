package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.driver.DriversRepository;
import com.pin2.pedrobino.domain.truck.Truck;
import com.pin2.pedrobino.domain.truck.TrucksRepository;
import com.pin2.pedrobino.support.ConvertTime;
import com.pin2.pedrobino.support.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
class NewProposalsGenerator {

    @Autowired
    private DriversRepository driversRepository;

    @Autowired
    private TrucksRepository trucksRepository;

    @Autowired
    private ProposalValueCalculator proposalValueCalculator;

    public List<Proposal> generateNewProposals(Date preferredDate,
                                               boolean canShare,
                                               City from,
                                               City to,
                                               int volume,
                                               long travelDuration,
                                               long distance) {
        List<Proposal> proposals = new ArrayList<>();

        getDatesAroundPreferredDate(preferredDate).forEach(dateStart -> {
            Proposal proposal = generateProposalForDate(
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

    private Proposal generateProposalForDate(Date dateStart,
                                             boolean canShare,
                                             City from,
                                             City to,
                                             int volume,
                                             long travelDuration,
                                             long distance) {
        Date dateEnd = DateUtil.plusSeconds(dateStart, travelDuration);

        List<Truck> availableTrucks = trucksRepository.findAvailableTrucks(
                dateStart,
                dateEnd,
                volume
        );

        List<Driver> availableDrivers = driversRepository.findAvailableDrivers(
                dateStart,
                dateEnd,
                from,
                distance
        );

        boolean needsTwoDrivers = ConvertTime.fromSecondsToHours(travelDuration) > 8.0;
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

        proposal.setValue(proposalValueCalculator.calculateValue(
                proposal.getDrivers(),
                proposal.getTruck(),
                travelDuration,
                distance
        ));

        proposal.setSharedValue(proposalValueCalculator.calculateSharedValue(proposal.getValue()));

        return proposal;
    }

    private List<Date> getDatesAroundPreferredDate(Date preferredDate) {
        List<Date> dates = new ArrayList<>();

        dates.add(DateUtil.minusDays(preferredDate, 1));
        dates.add(preferredDate);
        dates.add(DateUtil.plusDays(preferredDate, 1));


        return dates;
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
}
