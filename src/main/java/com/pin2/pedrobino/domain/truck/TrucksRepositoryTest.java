package com.pin2.pedrobino.domain.truck;

import com.pin2.pedrobino.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class TrucksRepositoryTest {

    @Autowired
    private TrucksRepository trucksRepository;

    @Before
    public void setUp() {

    }

    @Test
    public void shouldntFindSharedTrucks(){

//        trucksRepository.findAvailableTrucks(
//                        createDate("17/12/2016 01:00"),
//                        createDate("17/12/2016 07:00"),
//                        100.0
//                )
    }

}
