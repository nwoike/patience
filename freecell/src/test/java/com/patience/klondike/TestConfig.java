package com.patience.klondike;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;

@Configuration
public class TestConfig {

	@Bean
	public InMemoryGameRepository gameRepository() {
		return new InMemoryGameRepository();
	}
}
