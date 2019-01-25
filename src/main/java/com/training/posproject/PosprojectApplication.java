package com.training.posproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class})
public class PosprojectApplication{


    public static void main(String[] args) {
        SpringApplication.run(PosprojectApplication.class, args);
    }


}

