package com.patience.klondike.infrastructure.application;

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
	public SimpleCORSFilter corsFilter() {
		return new SimpleCORSFilter();
	}
	
	@Bean
	public DomainEventPublisherResetFilter domainEventPublisherResetFilter() {
		return new DomainEventPublisherResetFilter();
	}
}