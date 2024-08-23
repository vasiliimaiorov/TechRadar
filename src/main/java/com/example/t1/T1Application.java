package com.example.t1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class T1Application {

    public static void main(String[] args) {
        SpringApplication.run(T1Application.class, args);
    }

}
