package com.github.stasmalykhin.botHH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BotHHApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotHHApplication.class, args);
    }

}
