package com.patience.klondike.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.patience.domain.model.event.DomainEventSerializer;
import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.JdbcEventStore;

@Configuration
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
}
