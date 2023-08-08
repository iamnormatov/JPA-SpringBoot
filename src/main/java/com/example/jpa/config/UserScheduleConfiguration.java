package com.example.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
public class UserScheduleConfiguration {
    @Scheduled(fixedDelay = 2, initialDelay = 3, timeUnit = TimeUnit.SECONDS)
    private void print(){
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
    }
}
