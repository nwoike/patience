package com.patience.domain.model.event;

import static com.google.common.base.Preconditions.checkNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DomainEventSerializer {

	private ObjectMapper objectMapper;

	public DomainEventSerializer(ObjectMapper objectMapper) {
		this.objectMapper = checkNotNull(objectMapper, "ObjectMapper must be provided");		
	}
	
	public String serialize(DomainEvent event) {
		try {
			return objectMapper.writeValueAsString(event);
			
		} catch (JsonProcessingException e) {		
			throw new RuntimeException(e);
		}
	}
}
