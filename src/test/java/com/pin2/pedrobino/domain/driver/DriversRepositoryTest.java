package com.pin2.pedrobino.domain.driver;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.pin2.pedrobino.Application;
import com.pin2.pedrobino.domain.BaseTest;
import com.pin2.pedrobino.support.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static com.pin2.pedrobino.support.DateUtil.plusHours;
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
public class DriversRepositoryTest extends BaseTest {

    @Test
    public void shouldNotFindDrivers() {
        Date leavesAt = START_DATE;
        Date arrivesAt = plusHours(START_DATE, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                2000*1000
        );

        assertThat(drivers).isEmpty();
    }

    @Test
    public void shouldFindADriverInPortoAlegreDayBeforeADefinedRequest() {

        Date leavesAt = plusDays(START_DATE, 1);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                2000*1000
        );

        assertThat(drivers).hasSize(1);
    }

    @Test
    public void shouldFindADriverInPortoAlegreDayAfterADefinedRequest() {

        Date leavesAt = minusDays(START_DATE, 1);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                2000*1000
        );

        assertThat(drivers).hasSize(1);
    }

    @Test
    public void shouldFindADriverInPortoAlegreTwoDaysAfterADefinedRequest() {

        Date leavesAt = minusDays(START_DATE, 2);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                2000*1000
        );

        assertThat(drivers).hasSize(1);
    }

    @Test
    public void shouldFindADriverInPortoAlegreTwoDaysBeforeADefinedRequest() {

        Date leavesAt = plusDays(START_DATE, 2);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                2000*1000
        );

        assertThat(drivers).hasSize(1);
    }

    @Test
    public void shouldFindADriverInCuritiba() {
        Date leavesAt = plusDays(START_DATE, 2);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                CURITIBA,
                2000*1000
        );

        assertThat(drivers).hasSize(1);
    }

    @Test
    public void shouldNotFindADriverInFlorianopolis() {
        Date leavesAt = START_DATE;
        Date arrivesAt = plusHours(START_DATE, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                FLORIANOPOLIS,
                2000*1000
        );

        assertThat(drivers).isEmpty();
    }
}
