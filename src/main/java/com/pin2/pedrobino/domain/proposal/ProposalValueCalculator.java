package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.Truck;
import com.pin2.pedrobino.domain.driver.Driver;
import com.pin2.pedrobino.domain.settings.SettingsService;
import com.pin2.pedrobino.support.ConvertTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalValueCalculator {

    @Autowired
    private SettingsService settingsService;

    public double calculate(List<Driver> drivers,
                            Truck truck,
                            long travelDuration,
                            long distance,
                            boolean canShare) {
        double value = 0;

        // Drivers cost
        for (Driver driver : drivers) {
            value += driver.getHourlyWage()
                    * convertSecondsToHours(travelDuration)
                    * 2;
        }

        // Truck cost
        value += truck.getCostPerKm() * (distance / 1000) * 2;

        // Owner Profit Margin
        value += value * (getProfitMargin() / 100);

        // Conditional Shared discount
        if (canShare) {
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

    private double getProfitMargin() {
        return settingsService.getSettings().getProfitMargin();
    }
}
