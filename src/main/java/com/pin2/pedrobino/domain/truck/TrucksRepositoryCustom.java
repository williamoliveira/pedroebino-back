package com.pin2.pedrobino.domain.truck;

import java.util.Date;
import java.util.List;

public interface TrucksRepositoryCustom {
    List<Truck> findAvailableTrucks(Date dateStart, Date dateEnd, double volume);
}
