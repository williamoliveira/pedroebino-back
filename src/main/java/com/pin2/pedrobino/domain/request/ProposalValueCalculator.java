package com.pin2.pedrobino.domain.request;

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

        for (Driver driver : proposal.getDrivers()) {
            value += driver.getHourlyWage() * ConvertTime.fromSecondsToHours(
                    proposal.getRequest().getEstimatedTravelDuration()
            );
        }

        value += value * (settingsService.getSettings().getProfitMargin() / 100);

        return value;
    }
}
