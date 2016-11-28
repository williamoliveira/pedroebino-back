package com.pin2.pedrobino.domain.truck;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.pin2.pedrobino.Application;
import com.pin2.pedrobino.domain.BaseTest;
import com.pin2.pedrobino.domain.Truck;
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
public class TrucksRepositoryTest extends BaseTest {

    @Test
    public void shouldFind2AvailableTrucksOnTheDayOfADefinedRequest() {
        List<Truck> trucks = findTrucksOnDate(START_DATE);

        assertThat(trucks).hasSize(2);
    }

    @Test
    public void shouldFind3AvailableTrucksADayBeforeADefinedRequest() {
        Date dateStart = plusDays(START_DATE, 1);

        List<Truck> trucks = findTrucksOnDate(dateStart);

        assertThat(trucks).hasSize(3);
    }

    @Test
    public void shouldFind3AvailableTrucksADayAfterADefinedRequest() {
        Date dateStart = minusDays(START_DATE, 1);

        List<Truck> trucks = findTrucksOnDate(dateStart);

        assertThat(trucks).hasSize(3);
    }
    @Test
    public void shouldFind3AvailableTrucks2DaysBeforeADefinedRequest() {
        Date dateStart = plusDays(START_DATE, 2);

        List<Truck> trucks = findTrucksOnDate(dateStart);

        assertThat(trucks).hasSize(3);
    }

    @Test
    public void shouldFind3AvailableTrucks2DaysAfterADefinedRequest() {
        Date dateStart =  minusDays(START_DATE, 2);

        List<Truck> trucks = findTrucksOnDate(dateStart);

        assertThat(trucks).hasSize(3);
    }

    private List<Truck> findTrucksOnDate(Date dateStart){
        return trucksRepository.findAvailableTrucks(
                dateStart,
                plusHours(dateStart, 7),
                100.0
        );
    }
}
