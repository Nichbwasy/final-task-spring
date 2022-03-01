package com.epam.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.epam")
@EntityScan(basePackages = "com.epam")
@ComponentScan(basePackages = {"com.epam"})
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class);
    }
}
