package com.pin2.pedrobino.domain.driver;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriversRepository extends BaseRepository<Driver>, DriversRepositoryCustom {
}
