package com.pin2.pedrobino.domain.driver;

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

import java.util.Date;
import java.util.List;

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
                400
        );

        assertThat(drivers).isEmpty();
    }

    @Test
    public void shouldFindADriverInPortoAlegre() {

        Date leavesAt = plusDays(START_DATE, 2);
        Date arrivesAt = plusHours(leavesAt, 7);

        List<Driver> drivers = driversRepository.findAvailableDrivers(
                leavesAt,
                arrivesAt,
                PORTO_ALEGRE,
                400
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
                400
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
                400
        );

        assertThat(drivers).isEmpty();
    }
}
