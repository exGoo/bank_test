package com.bank.history;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HistoryApplication {
    public static void main(String[] args) {

        SpringApplication.run(HistoryApplication.class, args);

    }
}
