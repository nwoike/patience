package com.patience.domain.model.event;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DomainEventBroadcaster {

	private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventBroadcaster.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EventStore eventStore;

	private String exchangeName;
		
	public DomainEventBroadcaster(String exchangeName) {
		this.exchangeName = exchangeName;	
	}
	
	@Scheduled(fixedDelay=500)
	public void broadcastLastestEvents() {		
		List<StoredEvent> unprocessedEvents = eventStore.unprocessedEvents();
		
		for (StoredEvent storedEvent : unprocessedEvents) {
			Message message = MessageBuilder
				.withBody(storedEvent.eventBody().getBytes())
				.setContentType(MessageProperties.CONTENT_TYPE_JSON)
				.setMessageId(String.valueOf(storedEvent.eventId()))
				.build();
			
			rabbitTemplate.send(exchangeName, storedEvent.eventType(), message);
		}
			
		if (!unprocessedEvents.isEmpty()) {
			StoredEvent lastEvent = unprocessedEvents.get(unprocessedEvents.size() - 1);
			eventStore.updateLastProcessedEventId(lastEvent.eventId());
			LOGGER.debug("Found " + unprocessedEvents.size() + " events. Updated latest processed eventId to: " + lastEvent.eventId());
		}
	}
}
