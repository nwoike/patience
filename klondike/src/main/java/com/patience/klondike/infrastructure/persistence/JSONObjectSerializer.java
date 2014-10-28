package com.patience.klondike.infrastructure.persistence;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONObjectSerializer implements ObjectSerializer {

	private ObjectMapper objectMapper;
	
	public JSONObjectSerializer(ObjectMapper objectMapper) {
		this.objectMapper = checkNotNull(objectMapper, "Object Mapper must be provided.");	
	}
	
	@Override
	public String serialize(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
			
		} catch (JsonProcessingException e) {			
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public <T> T deserialize(String value, Class<T> type) {
		try {
			return (T) objectMapper.readValue(value, type);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
