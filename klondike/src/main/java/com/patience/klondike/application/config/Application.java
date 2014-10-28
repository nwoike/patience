package com.patience.klondike.application.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.patience.servlet.filter.DomainEventPublisherResetFilter;
import com.patience.servlet.filter.SimpleCORSFilter;

@Configuration
@EnableAutoConfiguration
@EnableScheduling
@Import(ApplicationConfig.class)
public class Application {
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Bean
	SimpleCORSFilter corsFilter() {
		return new SimpleCORSFilter();
	}
	
	@Bean
	DomainEventPublisherResetFilter domainEventPublisherResetFilter() {
		return new DomainEventPublisherResetFilter();
	}
}