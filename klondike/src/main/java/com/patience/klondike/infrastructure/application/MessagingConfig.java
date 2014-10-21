package com.patience.klondike.infrastructure.application;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.patience.domain.model.event.DomainEventBroadcaster;
import com.patience.klondike.ports.adapter.messaging.GameCreatedListener;


@Configuration
public class MessagingConfig {

	static final String EXCHANGE = "patienceKlondike";
	
	static final String queueName = "GameCreatedListener";

	@Bean
	Queue gameCreatedQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE);
	}
	
	@Bean
	Binding binding(Queue gameCreatedQueue, TopicExchange exchange) { 
		return BindingBuilder.bind(gameCreatedQueue).to(exchange).with("patience.klondike.GameCreated");
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(gameCreatedListener());

		return container;
	}

	@Bean
	public DomainEventBroadcaster domainEventBroadcaster() {
		return new DomainEventBroadcaster(EXCHANGE);
	}
	
	@Bean
    GameCreatedListener gameCreatedListener() {
        return new GameCreatedListener();
    }
}
