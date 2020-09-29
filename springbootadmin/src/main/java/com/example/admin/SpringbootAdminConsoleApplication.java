package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@ComponentScan("com.example.admin")
public class SpringbootAdminConsoleApplication implements HealthIndicator {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAdminConsoleApplication.class, args);
    }

    @Override
    public Health health() {
        return Health.up().withDetail("hello", "Hello").build();
    }

}
