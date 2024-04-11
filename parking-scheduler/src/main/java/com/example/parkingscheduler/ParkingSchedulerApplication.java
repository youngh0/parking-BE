package com.example.parkingscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.example.parking"})
@EnableScheduling
public class ParkingSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingSchedulerApplication.class, args);
    }

}
