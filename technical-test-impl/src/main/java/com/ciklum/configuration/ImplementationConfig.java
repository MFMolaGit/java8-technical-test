package com.ciklum.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.ciklum"})
public class ImplementationConfig {

    public static void main(String[] args) {
        SpringApplication.run(ImplementationConfig.class, args);
    }

}