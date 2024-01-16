package com.example.parking;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ParkingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ParkingApplication.class, args);
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
        System.out.println(LocalTime.parse("1200", TIME_FORMATTER));
        System.out.println(LocalTime.parse("12:00", TIME_FORMATTER));

//        for (String beanDefinitionName : run.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }

        Object parkingScheduler = run.getBean("parkingScheduler");
        int a = 1 + 2;
    }
}
