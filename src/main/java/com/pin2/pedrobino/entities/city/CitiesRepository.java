package com.pin2.pedrobino.entities.city;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends BaseRepository<City> {
    City findByName(String name);
}
