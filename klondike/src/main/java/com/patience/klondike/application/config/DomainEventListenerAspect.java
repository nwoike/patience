package com.patience.klondike.application.config;

import static com.google.common.base.Preconditions.checkNotNull;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.google.common.eventbus.Subscribe;
import com.patience.domain.model.event.DomainEvent;
import com.patience.domain.model.event.DomainEventPublisher;
import com.patience.domain.model.event.DomainEventSubscriber;
import com.patience.domain.model.event.EventStore;

@Aspect
public class DomainEventListenerAspect {

	private final DomainEventPersister domainEventPersister = new DomainEventPersister();
	
	private EventStore eventStore;

	public DomainEventListenerAspect(EventStore eventStore) {
		this.eventStore = checkNotNull(eventStore, "Event store must be provided.");
	}

	@Before("execution( * com.patience.klondike.application.*.*(..))")
	void handle() {
		DomainEventPublisher
			.instance()
			.subscribe(domainEventPersister);
	}
	
	final class DomainEventPersister implements DomainEventSubscriber {
		@Subscribe
		public void appendEventToEventStore(DomainEvent domainEvent) {
			eventStore.append(domainEvent);
		}
	}
	
}