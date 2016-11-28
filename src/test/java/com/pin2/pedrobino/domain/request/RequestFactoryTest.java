package com.pin2.pedrobino.domain.request;

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
public class RequestFactoryTest extends BaseTest {

    @Test
    public void shouldCreateARequestWith2NewProposals1DayBeforeADefinedRequest(){
        Request request = requestFactory.create(new Request(
                false,
                plusDays(START_DATE, 1),
                500,
                PORTO_ALEGRE,
                CURITIBA,
                CLIENT1
        ));

        assertThat(request).isNotNull();
        assertThat(request.getProposals()).hasSize(2);
    }

    @Test
    public void shouldCreateARequestWith3NewProposals2DaysBeforeADefinedRequest(){
        Request request = requestFactory.create(new Request(
                false,
                plusDays(START_DATE, 2),
                500,
                PORTO_ALEGRE,
                CURITIBA,
                CLIENT1
        ));

        assertThat(request).isNotNull();
        assertThat(request.getProposals()).hasSize(3);
    }

}
