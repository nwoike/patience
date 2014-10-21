package com.patience.klondike.infrastructure.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.domain.model.GameRepository;
import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;
import com.patience.klondike.resource.GameResource;

@Configuration
public class ApplicationConfig {

	@Bean
	public GameRepository gameRepository() {
		//return new JdbcGameRepository();
		return new InMemoryGameRepository();
	}
	
	@Bean
	public GameApplicationService gameApplicationService() {
		return new GameApplicationService(gameRepository());
	}
	
	@Bean
	public GameResource gameResource() {
		return new GameResource(gameApplicationService());
	}	
}