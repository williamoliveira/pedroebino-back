package com.pin2.pedrobino.domain.driver;

import com.pin2.pedrobino.domain.Truck;
import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.proposal.QProposal;
import com.pin2.pedrobino.domain.request.QRequest;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class DriversRepositoryImpl extends QueryDslRepositorySupport implements DriversRepositoryCustom {

    public DriversRepositoryImpl() {
        super(Truck.class);
    }

    public List<Driver> findAvailableDrivers(Date dateStart, Date dateEnd, City city, long distance) {
        QDriver driver = QDriver.driver;
        QRequest request = QRequest.request;
        QProposal proposal = QProposal.proposal;

        return from(request)
                .rightJoin(request.chosenProposal, proposal) // right join so we get proposals without requests
                .rightJoin(proposal.drivers, driver) // right join so we get drivers without proposals
                .where(driver.city.eq(city) // driver lives in city
                        .and(driver.maxDistance.goe(distance / 1000)) // max distance driver want to drive
                        .and(proposal.isNull() // where drivers has no proposals
                                .or(driver.notIn(
                                        from(proposal).where(proposal.leavesAt.lt(dateEnd)
                                                .and(proposal.arrivesAt.gt(dateStart))).select(proposal.drivers.any())
                                        )
                                ))
                )
                .select(driver) // select only drivers (must)
                .distinct()
                .orderBy(driver.hourlyWage.asc(), driver.experience.desc())
                .fetch();
    }

}
