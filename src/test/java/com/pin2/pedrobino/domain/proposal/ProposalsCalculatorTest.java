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

import static com.pin2.pedrobino.support.DateUtil.minusDays;
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
    public void shouldGenerate3NewProposals() {
        Date date = plusDays(START_DATE, 2);

        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                date,
                true, // Can be shared!
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).hasSize(3);

        for (Proposal proposal : proposals) {
            assertThat(proposal).hasFieldOrPropertyWithValue("id", 0L);
        }
    }

    @Test
    public void shouldFind1SharedProposalAndGenerate2OnADayThatHasADefinedRequest() {
        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                START_DATE,
                true,  // Can be shared!
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).hasSize(3);
        assertThat(proposals.get(0).getId()).isZero();
        assertThat(proposals.get(1).getId()).isNotZero();
        assertThat(proposals.get(2).getId()).isZero();
    }

    @Test
    public void shouldGenerate2ProposalsOnADayThatHasADefinedRequest() {
        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                START_DATE,
                false,  // Can NOT be shared!
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                100,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        // 2
        assertThat(proposals).hasSize(2);

        // Is new
        assertThat(proposals.get(0).getId()).isZero();
        assertThat(proposals.get(1).getId()).isZero();

        // Is date before and after
        assertThat(proposals.get(0).getLeavesAt()).isEqualTo(minusDays(START_DATE, 1));
        assertThat(proposals.get(1).getLeavesAt()).isEqualTo(plusDays(START_DATE, 1));
    }

    @Test
    public void shouldGenerate2ProposalsAndNoSharedProposalsBecauseOfVolume() {
        List<Proposal> proposals = proposalsCalculator.calculateProposals(
                START_DATE,
                true,  // Can be shared!
                PORTO_ALEGRE,
                FLORIANOPOLIS,
                700,
                5 * 60 * 60, // 5 hours
                500 * 1000 // 500 km
        );

        assertThat(proposals).hasSize(2);

        // Is new
        assertThat(proposals.get(0).getId()).isZero();
        assertThat(proposals.get(1).getId()).isZero();

        assertThat(proposals.get(0).getLeavesAt()).isEqualTo(minusDays(START_DATE, 1));
        assertThat(proposals.get(1).getLeavesAt()).isEqualTo(plusDays(START_DATE, 1));
    }
}
