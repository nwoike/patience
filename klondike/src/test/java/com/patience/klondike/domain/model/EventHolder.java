package com.patience.klondike.domain.model;

import com.patience.domain.model.event.DomainEvent;

public class EventHolder {
	
	private DomainEvent event;
	
	public void setEvent(DomainEvent event) {
		this.event = event;
	}
	
	public DomainEvent getEvent() {
		return event;
	}		
}