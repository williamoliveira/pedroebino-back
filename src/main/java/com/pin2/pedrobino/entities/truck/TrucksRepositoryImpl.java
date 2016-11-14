package com.pin2.pedrobino.entities.truck;

import com.pin2.pedrobino.entities.request.QProposal;
import com.pin2.pedrobino.entities.request.QRequest;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

@Repository
public class TrucksRepositoryImpl extends QueryDslRepositorySupport implements TrucksRepositoryCustom {

    public TrucksRepositoryImpl() {
        super(Truck.class);
    }

    public List<Truck> findAvailableTrucks(LocalDateTime dateStart, LocalDateTime dateEnd, double volume) {
        QTruck truck = QTruck.truck;
        QRequest request = QRequest.request;
        QProposal proposal = QProposal.proposal;

        return from(request)
                .rightJoin(request.chosenProposal, proposal) // right join so we get proposals without requests
                .rightJoin(proposal.truck, truck) // right join so we get trucks without proposals
                .where(
                        truck.volume.goe(volume) // truck can handle >= volume
                                .and(proposal.leavesAt.isNull() // where truck has no proposals
                                        .or(proposal.leavesAt.notBetween(dateStart, dateEnd) // or where proposals dates do not collides
                                                .and(proposal.arrivesAt.notBetween(dateStart, dateEnd))))
                )
                .select(truck) // select only trucks (must)
                .orderBy(truck.costPerKm.asc())
                .fetch();
    }
}
