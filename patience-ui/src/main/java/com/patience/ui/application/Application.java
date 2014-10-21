package com.patience.ui.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}