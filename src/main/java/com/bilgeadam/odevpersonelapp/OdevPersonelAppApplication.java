package com.bilgeadam.odevpersonelapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OdevPersonelAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(OdevPersonelAppApplication.class, args);
    }

}
