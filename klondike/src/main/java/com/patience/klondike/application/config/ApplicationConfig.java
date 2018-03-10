package com.patience.klondike.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.klondike.infrastructure.persistence.JSONObjectSerializer;
import com.patience.klondike.infrastructure.persistence.ObjectSerializer;
import com.patience.servlet.filter.DomainEventPublisherResetFilter;
import com.patience.servlet.filter.SimpleCORSFilter;

@Configuration
@EnableScheduling
@PropertySource(value = "classpath:sql.properties")
public class ApplicationConfig {

    @Bean
    SimpleCORSFilter corsFilter() {
        return new SimpleCORSFilter();
    }

    @Bean
    DomainEventPublisherResetFilter domainEventPublisherResetFilter() {
        return new DomainEventPublisherResetFilter();
    }

    @Bean
    ObjectSerializer objectSerializer(ObjectMapper objectMapper) {
        return new JSONObjectSerializer(objectMapper);
    }
}