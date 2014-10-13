package com.patience.klondike.infrastructure.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(ApplicationConfig.class)
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}