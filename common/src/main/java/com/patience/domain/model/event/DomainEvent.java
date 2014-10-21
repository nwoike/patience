package com.patience.domain.model.event;

import org.joda.time.DateTime;

public interface DomainEvent {

	int eventVersion();

	String eventType();
	
	DateTime occurredOn();
	
}