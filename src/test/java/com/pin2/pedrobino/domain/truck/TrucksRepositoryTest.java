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
public class TrucksRepositoryTest extends BaseTest {

    @Test
    public void shouldFind2SharedTrucks() {
        List<Truck> trucks = trucksRepository.findAvailableTrucks(
                START_DATE,
                plusHours(START_DATE, 7),
                100.0
        );

        assertThat(trucks).hasSize(2);
    }

    @Test
    public void shouldFind3SharedTrucks() {
        List<Truck> trucks = trucksRepository.findAvailableTrucks(
                plusDays(START_DATE, 2),
                plusHours(plusDays(START_DATE, 2), 7),
                100.0
        );

        assertThat(trucks).hasSize(3);
    }

}
