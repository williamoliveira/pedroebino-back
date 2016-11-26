package com.pin2.pedrobino.domain.proposal;

import com.pin2.pedrobino.domain.city.City;

import java.time.ZonedDateTime;
import java.util.Date;

public interface ProposalsRepositoryCustom {
    Proposal findBestShareableProposal(Date dateStart, City from, City to, double volume);
}
