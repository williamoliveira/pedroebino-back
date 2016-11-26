package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.settings.SettingsService;
import com.pin2.pedrobino.support.ConvertTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalValueCalculator {

    @Autowired
    private SettingsService settingsService;

    public double calculate(Proposal proposal) {
        double value = 0;

        // Drivers cost
        for (Driver driver : proposal.getDrivers()) {
            value += driver.getHourlyWage()
                    * convertSecondsToHours(proposal.getRequests().get(0).getEstimatedTravelDuration())
                    * 2;
        }

        // Truck cost
        value += proposal.getTruck().getCostPerKm() * (proposal.getRequests().get(0).getDistance() / 1000) * 2;

        // Owner Profit Margin
        value += value * (getProfitMargin() / 100);

        // Conditional Shared discount
        if(proposal.getRequests().get(0).isCanShare()){
            value -= value * (getSharedDiscount() / 100);
        }

        return value;
    }

    private double getSharedDiscount() {
        return settingsService.getSettings().getSharedDiscount();
    }

    private double convertSecondsToHours(long seconds) {
        return ConvertTime.fromSecondsToHours(seconds);
    }

    private double getProfitMargin(){
        return settingsService.getSettings().getProfitMargin();
    }
}
