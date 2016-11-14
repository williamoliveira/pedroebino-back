package com.pin2.pedrobino.entities.driver;

import com.pin2.pedrobino.entities.city.City;

import java.time.LocalDateTime;
import java.util.List;

public interface DriversRepositoryCustom {
    List<Driver> findAvailableDrivers(LocalDateTime dateStart, LocalDateTime dateEnd, City city, long distance);
}
