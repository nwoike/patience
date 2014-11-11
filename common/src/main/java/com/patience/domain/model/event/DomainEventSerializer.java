package com.patience.domain.model.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Serializes domain events by Field only.
 */
public class DomainEventSerializer {

	private ObjectMapper objectMapper;

	public DomainEventSerializer() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JodaModule());
		objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		this.objectMapper = objectMapper;		
	}
	
	public String serialize(DomainEvent event) {
		try {
			return objectMapper.writeValueAsString(event);
			
		} catch (JsonProcessingException e) {		
			throw new RuntimeException(e);
		}
	}
}
