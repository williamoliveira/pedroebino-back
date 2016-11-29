package com.pin2.pedrobino.domain.proposal;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.pin2.pedrobino.Application;
import com.pin2.pedrobino.domain.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import static com.pin2.pedrobino.support.DateUtil.plusDays;
import static org.assertj.core.api.Assertions.assertThat;

@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@Transactional
public class ProposalsRepositoryTest extends BaseTest {

    @Test
    public void shouldFindAProposal() {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                START_DATE,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100
        );

        assertThat(proposal).isNotNull();
    }

    @Test
    public void shouldNotFindAProposalBecauseOfTruckVolume() {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                START_DATE,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                800
        );

        assertThat(proposal).isNull();
    }

    @Test
    public void shouldNotFindAProposalBecauseOfDate() {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                plusDays(START_DATE, 2),
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100
        );

        assertThat(proposal).isNull();
    }

    @Test
    public void shouldNotFindAProposalBecauseOfFromCity() {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                START_DATE,
                CURITIBA,
                FLORIANOPOLIS,
                100
        );

        assertThat(proposal).isNull();
    }

    @Test
    public void shouldNotFindAProposalBecauseOfToCity() {
        Proposal proposal = proposalsRepository.findBestShareableProposal(
                START_DATE,
                PORTO_ALEGRE,
                CURITIBA,
                100
        );

        assertThat(proposal).isNull();
    }


}
