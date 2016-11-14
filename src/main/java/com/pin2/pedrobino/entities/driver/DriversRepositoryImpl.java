package com.pin2.pedrobino.entities.driver;

import com.pin2.pedrobino.entities.city.City;
import com.pin2.pedrobino.entities.request.QProposal;
import com.pin2.pedrobino.entities.request.QRequest;
import com.pin2.pedrobino.entities.truck.Truck;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DriversRepositoryImpl extends QueryDslRepositorySupport implements DriversRepositoryCustom {

    public DriversRepositoryImpl() {
        super(Truck.class);
    }

    public List<Driver> findAvailableDrivers(LocalDateTime dateStart, LocalDateTime dateEnd, City city, long distance) {
        QDriver driver = QDriver.driver;
        QRequest request = QRequest.request;
        QProposal proposal = QProposal.proposal;

        return from(request)
                .rightJoin(request.chosenProposal, proposal) // right join so we get proposals without requests
                .rightJoin(proposal.drivers, driver) // right join so we get drivers without proposals
                .where(
                        driver.city.eq(city) // driver lives in city
                                .and(driver.maxDistance.loe(distance)) // max distance driver want to drive
                                .and(proposal.leavesAt.isNull() // where drivers has no proposals
                                        .or(proposal.leavesAt.notBetween(dateStart, dateEnd)// or where proposals dates do not collides
                                                .and(proposal.arrivesAt.notBetween(dateStart, dateEnd))))
                )
                .select(driver) // select only drivers (must)
                .orderBy(driver.hourlyWage.asc(), driver.experience.desc())
                .fetch();
    }

}
