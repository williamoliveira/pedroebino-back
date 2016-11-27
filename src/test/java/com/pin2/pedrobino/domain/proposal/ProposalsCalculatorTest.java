package com.pin2.pedrobino.domain.proposal;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.pin2.pedrobino.Application;
import com.pin2.pedrobino.domain.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

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
public class ProposalsCalculatorTest extends BaseTest {

    @Autowired
    protected ProposalsCalculator proposalsCalculator;

    @Test
    public void shouldGenerate2NewProposals() {
        Date date = plusDays(START_DATE, 2);

        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                date,
                true,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).hasSize(2);

        for (Proposal proposal : proposals) {
            assertThat(proposal).hasFieldOrPropertyWithValue("id", 0L);
        }
    }

    @Test
    public void shouldFind1SharedProposalOnADayThatHasADefinedRequest() {
        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                START_DATE,
                true,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).hasSize(1);
        assertThat(proposals.get(0).getId()).isNotZero();
    }

    @Test
    public void shouldNotReturnProposalsOnADayThatHasADefinedRequest() {
        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                START_DATE,
                false,
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).isEmpty();
    }
}
