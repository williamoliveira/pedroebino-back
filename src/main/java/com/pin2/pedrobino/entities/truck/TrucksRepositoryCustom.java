package com.pin2.pedrobino.entities.truck;

import java.time.LocalDateTime;
import java.util.List;

public interface TrucksRepositoryCustom {
    List<Truck> findAvailableTrucks(LocalDateTime dateStart, LocalDateTime dateEnd, double volume);
}
