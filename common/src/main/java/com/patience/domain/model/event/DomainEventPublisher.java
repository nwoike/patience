package com.patience.domain.model.event;

import com.google.common.eventbus.EventBus;

/**
 * Not yet throughly tested for Thread Safety. Will require a Servlet Filter to call reset.
 */
public class DomainEventPublisher {
	
	private static final ThreadLocal<DomainEventPublisher> instance = new ThreadLocal<DomainEventPublisher>() {
		protected DomainEventPublisher initialValue() {
			return new DomainEventPublisher();			
		};
	};
	
	private EventBus eventBus = new EventBus();
	
	private DomainEventPublisher() {		
	}
	
	public static DomainEventPublisher instance() {
		return instance.get();
	}
	
	public void publish(DomainEvent domainEvent) {
		eventBus.post(domainEvent);	
	}
	
	public void subscribe(DomainEventSubscriber subscriber) {
		eventBus.register(subscriber);
	}
	
	public void reset() {		
		instance.get().setEventBus(new EventBus());
	}

	private void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
}