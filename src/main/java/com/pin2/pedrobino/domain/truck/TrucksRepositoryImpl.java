package com.pin2.pedrobino.domain.truck;

import com.pin2.pedrobino.domain.proposal.QProposal;
import com.pin2.pedrobino.domain.request.QRequest;
import com.pin2.pedrobino.domain.request.RequestStatus;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Repository
public class TrucksRepositoryImpl extends QueryDslRepositorySupport implements TrucksRepositoryCustom {

    public TrucksRepositoryImpl() {
        super(Truck.class);
    }

    public List<Truck> findAvailableTrucks(Date dateStart, Date dateEnd, double volume) {
        QTruck truck = QTruck.truck;
        QRequest request = QRequest.request;
        QProposal proposal = QProposal.proposal;
        QProposal chosenProposal = new QProposal("choosenProposal");

        return from(request)
                .rightJoin(request.chosenProposal, proposal) // right join so we get proposals without requests
                .rightJoin(proposal.truck, truck) // right join so we get trucks without proposals
                .where(truck.volume.goe(volume)
                        .and(proposal.isNull() // where drivers has no proposals
                                .or(truck.notIn(
                                        from(proposal)
                                                .where(proposal.leavesAt.lt(dateEnd).and(proposal.arrivesAt.gt(dateStart)))
                                                .select(proposal.truck))
                                ))
                )
                .select(truck) // select only trucks (must)
                .distinct()
                .orderBy(truck.costPerKm.asc())
                .fetch();
    }
}
