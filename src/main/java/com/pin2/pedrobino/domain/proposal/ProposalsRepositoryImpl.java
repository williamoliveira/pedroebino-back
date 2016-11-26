package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.city.City;
import com.pin2.pedrobino.domain.request.QRequest;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Date;

@Repository
public class ProposalsRepositoryImpl extends QueryDslRepositorySupport implements ProposalsRepositoryCustom {

    public ProposalsRepositoryImpl() {
        super(Proposal.class);
    }

    @Override
    public Proposal findBestShareableProposal(Date dateStart, City from, City to, double volume) {

        QProposal qProposal = QProposal.proposal;
        QRequest qRequest = QRequest.request;

        return from(qProposal)
                .innerJoin(qProposal.definedRequests, qRequest)
                .where(
//                        qProposal.leavesAt.eq(dateStart)
                        qRequest.from.eq(from)
                        .and(qRequest.to.eq(to))
                )
//                .groupBy(qProposal.id, qRequest.volume, qProposal.truck.volume)
//                .having(qRequest.volume.sum().add(volume).loe(qProposal.truck.volume))
                .orderBy(qProposal.value.asc())
                .distinct()
                .fetchFirst();
    }
}
