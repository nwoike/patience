package com.patience.klondike.infrastructure.persistence;

public interface ObjectSerializer {

	String serialize(Object object);

	<T> T deserialize(String value, Class<T> type);

}