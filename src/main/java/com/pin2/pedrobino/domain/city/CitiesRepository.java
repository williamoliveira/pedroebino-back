package com.pin2.pedrobino.domain.city;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends BaseRepository<City> {
    City findByName(String name);
}
