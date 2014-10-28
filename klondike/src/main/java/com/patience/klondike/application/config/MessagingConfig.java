package com.patience.klondike.application.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.patience.domain.model.event.DomainEventBroadcaster;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.infrastructure.messaging.GameScorerListener;


@Configuration
public class MessagingConfig {

	static final String EXCHANGE = "patienceKlondike";
	
	static final String queueName = "GameScorerListener";

	@Autowired
	private GameScoreApplicationService scoreService;
	
	@Bean 
	RetryTemplate retryTemplate(RabbitTemplate rabbitTemplate) {
		RetryTemplate retryTemplate = new RetryTemplate();
	
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(5);
		retryTemplate.setRetryPolicy(retryPolicy);
		
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(500);
		backOffPolicy.setMultiplier(1.2);
		backOffPolicy.setMaxInterval(5000);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		
		rabbitTemplate.setRetryTemplate(retryTemplate);

		return retryTemplate;
	}
	
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
		return BindingBuilder.bind(gameCreatedQueue).to(exchange).with("patience.klondike.*");
	}
	
	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(gameScorerListener());

		return container;
	}

	@Bean
	DomainEventBroadcaster domainEventBroadcaster() {
		return new DomainEventBroadcaster(EXCHANGE);
	}
	
	@Bean
    GameScorerListener gameScorerListener() {
        return new GameScorerListener(scoreService);
    }
}
