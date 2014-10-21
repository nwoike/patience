package com.patience.klondike.infrastructure.application;

import static com.google.common.base.Preconditions.checkNotNull;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.google.common.eventbus.Subscribe;
import com.patience.domain.model.event.DomainEvent;
import com.patience.domain.model.event.DomainEventPublisher;
import com.patience.domain.model.event.DomainEventSubscriber;
import com.patience.domain.model.event.EventStore;

@Aspect
public class DomainEventProcessor {

	private EventStore eventStore;

	public DomainEventProcessor(EventStore eventStore) {
		this.eventStore = checkNotNull(eventStore, "Event store must be provided.");
	}

	@Before("execution( * com.patience.klondike.application.*.*(..))")
	public void handle() {
		DomainEventPublisher
			.instance()
			.subscribe(new DomainEventSubscriber() {
				@Subscribe
				public void appendEventToEventStore(DomainEvent domainEvent) {
					eventStore.append(domainEvent);
				}
			});
	}
}