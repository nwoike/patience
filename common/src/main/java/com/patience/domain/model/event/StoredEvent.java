package com.patience.domain.model.event;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StoredEvent {

	private String eventType;
	
	private String eventBody;

	private DateTime occurredOn;
	
	private long eventId;
	
	public StoredEvent(String eventType, DateTime occurredOn, String eventBody) {
		this.eventType = eventType;
		this.occurredOn = occurredOn;
		this.eventBody = eventBody;		
	}
	
	@JsonCreator
	public StoredEvent(long eventId, String eventType, DateTime occurredOn, String eventBody) {
		this(eventType, occurredOn, eventBody);
		this.eventId = eventId;	
	}

	@JsonProperty
	public long eventId() {
		return eventId;
	}
	
	@JsonProperty
	public String eventType() {
		return eventType;
	}
	
	@JsonProperty
	public DateTime occurredOn() {
		return occurredOn;
	}
	
	@JsonProperty
	public String eventBody() {
		return eventBody;
	}
}
