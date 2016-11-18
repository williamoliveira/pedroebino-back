package com.pin2.pedrobino.domain.truck;

import com.pin2.pedrobino.domain.request.QProposal;
import com.pin2.pedrobino.domain.request.QRequest;
import com.pin2.pedrobino.domain.request.RequestStatus;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
                                .and(request.status.in(RequestStatus.CANCELED, RequestStatus.COMPLETED)
                                        .or(proposal.leavesAt.isNull() // where drivers has no proposals
                                                .or(proposal.leavesAt.notBetween(dateStart, dateEnd)// or where proposals dates do not collides
                                                        .and(proposal.arrivesAt.notBetween(dateStart, dateEnd))
                                                )
                                        )
                                )
                )
                .select(truck) // select only trucks (must)
                .orderBy(truck.costPerKm.asc())
                .fetch();
    }
}
