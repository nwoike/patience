package com.patience.klondike;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.domain.model.event.DomainEventSerializer;
import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.JdbcEventStore;
import com.patience.klondike.infrastructure.persistence.JSONObjectSerializer;
import com.patience.klondike.infrastructure.persistence.ObjectSerializer;
import com.patience.servlet.filter.DomainEventPublisherResetFilter;

@Configuration
@EnableScheduling
public class EventsConfig {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Bean
	DomainEventSerializer domainEventSerializer() {
		return new DomainEventSerializer();
	}
	
	@Bean
	EventStore jdbcEventStore() {
		return new JdbcEventStore(domainEventSerializer(), jdbcTemplate);
	}
	
	@Bean
	DomainEventListenerAspect domainEventProcessor() {
		return new DomainEventListenerAspect(jdbcEventStore());
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
