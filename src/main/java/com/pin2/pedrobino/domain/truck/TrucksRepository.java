package com.pin2.pedrobino.domain.truck;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrucksRepository extends BaseRepository<Truck>, TrucksRepositoryCustom {

//    @Query("SELECT t FROM Agenda a " +
//            "RIGHT JOIN a.request r " +
//            "RIGHT JOIN r.choosenProposal p " +
//            "RIGHT JOIN p.truck t " +
//            "WHERE p.startsAt > :startsAt OR p.endsAt < :endsAt")
//    public List<Truck> getAvailableTrucks(
//            @Param("startsAt") ZonedDateTime startsAt,
//            @Param("endsAt") ZonedDateTime endsAt
//    );
}
