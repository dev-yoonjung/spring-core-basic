package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = applicationContext.getBean(StatefulService.class);
        StatefulService statefulService2 = applicationContext.getBean(StatefulService.class);

        // ThreadA: 사용자A 10,000원 주문
        int priceA = statefulService1.order("userA", 10000);
        // ThreadB: 사용자B 20,000원 주문
        int priceB = statefulService1.order("userB", 20000);

        // ThreadA: 사용자A 주문 금액 조회
        System.out.println("price = " + priceA);

        assertThat(priceA).isEqualTo(10000);
        assertThat(priceB).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

    }

}