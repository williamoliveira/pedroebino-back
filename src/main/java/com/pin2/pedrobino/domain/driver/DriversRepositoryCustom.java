package com.pin2.pedrobino.domain.driver;

import com.pin2.pedrobino.domain.city.City;

import java.util.Date;
import java.util.List;

public interface DriversRepositoryCustom {
    List<Driver> findAvailableDrivers(Date dateStart, Date dateEnd, City city, long distance);
}
