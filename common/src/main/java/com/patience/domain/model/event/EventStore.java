package com.patience.domain.model.event;

import java.util.List;

public interface EventStore {

	void append(DomainEvent aDomainEvent);
	
	List<StoredEvent> unprocessedEvents();

	void updateLastProcessedEventId(long eventId);

	List<StoredEvent> allEvents();
	
}
