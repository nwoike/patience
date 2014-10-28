package com.patience.domain.model;

import com.patience.domain.model.event.DomainEvent;
import com.patience.domain.model.event.DomainEventPublisher;

public abstract class Entity {

	private int version;
	
	protected Entity() {		
	}
	
	public void publish(DomainEvent event) {
		DomainEventPublisher
			.instance()
			.publish(event);
	}
	
	public int version() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}