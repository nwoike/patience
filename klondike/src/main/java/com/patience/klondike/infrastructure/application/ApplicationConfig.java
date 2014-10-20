package com.patience.klondike.infrastructure.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.domain.model.game.SimpleWinnableChecker;
import com.patience.klondike.infrastructure.persistence.JdbcGameRepository;
import com.patience.klondike.resource.GameResource;

@Configuration
public class ApplicationConfig {

	@Bean
	public GameRepository gameRepository() {
		return new JdbcGameRepository();
	}
	
	@Bean
	public GameApplicationService gameApplicationService() {
		return new GameApplicationService(gameRepository(), new SimpleWinnableChecker());
	}
	
	@Bean
	public GameResource gameResource() {
		return new GameResource(gameApplicationService());
	}	
}