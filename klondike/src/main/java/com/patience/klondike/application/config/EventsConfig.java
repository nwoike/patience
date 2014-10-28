package com.patience.klondike.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.domain.model.event.DomainEventSerializer;
import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.JdbcEventStore;

@Import(MessagingConfig.class)
public class EventsConfig {

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Bean
	DomainEventSerializer domainEventSerializer() {
		return new DomainEventSerializer(objectMapper);
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