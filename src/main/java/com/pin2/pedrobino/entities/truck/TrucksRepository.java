package com.pin2.pedrobino.entities.truck;

import com.pin2.pedrobino.support.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrucksRepository extends BaseRepository<Truck>, TrucksRepositoryCustom {

//    @Query("SELECT t FROM Agenda a " +
//            "RIGHT JOIN a.request r " +
//            "RIGHT JOIN r.choosenProposal p " +
//            "RIGHT JOIN p.truck t " +
//            "WHERE p.startsAt > :startsAt OR p.endsAt < :endsAt")
//    public List<Truck> getAvailableTrucks(
//            @Param("startsAt") LocalDateTime startsAt,
//            @Param("endsAt") LocalDateTime endsAt
//    );
}
