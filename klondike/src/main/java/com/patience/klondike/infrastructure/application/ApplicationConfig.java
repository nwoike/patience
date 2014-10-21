package com.patience.klondike.infrastructure.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.application.GameScorerApplicationService;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.domain.model.game.scorer.GameScorerRepository;
import com.patience.klondike.domain.service.game.SimpleWinChecker;
import com.patience.klondike.infrastructure.persistence.JdbcGameRepository;
import com.patience.klondike.infrastructure.persistence.JdbcGameScorerRepository;
import com.patience.klondike.resource.GameResource;
import com.patience.klondike.resource.NotificationResource;

@Configuration
@Import(EventsConfig.class)
public class ApplicationConfig {

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Bean
	GameRepository gameRepository() {
		return new JdbcGameRepository(jdbcTemplate, objectMapper);
	}
	
	@Bean
	GameApplicationService gameApplicationService() {
		return new GameApplicationService(gameRepository(), new SimpleWinChecker());
	}
	
	@Bean
	GameScorerApplicationService gameScorerApplicationService() {
		return new GameScorerApplicationService(gameScorerRepository());
	}
	
	@Bean
	GameScorerRepository gameScorerRepository() {
		return new JdbcGameScorerRepository();
	}

	@Bean
	GameResource gameResource() {
		return new GameResource(gameApplicationService());
	}	
	
	@Bean
	NotificationResource NotificationResource() {
		return new NotificationResource();
	}
	

}