package com.patience.klondike.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.domain.model.game.score.DefaultScoringStrategy;
import com.patience.klondike.domain.model.game.score.GameScoreRepository;
import com.patience.klondike.domain.service.game.SimpleWinChecker;
import com.patience.klondike.infrastructure.persistence.JSONObjectSerializer;
import com.patience.klondike.infrastructure.persistence.JdbcGameRepository;
import com.patience.klondike.infrastructure.persistence.JdbcGameScoreRepository;
import com.patience.klondike.infrastructure.persistence.ObjectSerializer;
import com.patience.klondike.resource.GameResource;
import com.patience.klondike.resource.NotificationResource;

@Configuration
@Import(EventsConfig.class)
@PropertySource(value="classpath:sql.properties")
public class ApplicationConfig {

	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Bean
	GameRepository gameRepository() {
		return new JdbcGameRepository(jdbcTemplate, objectSerializer());
	}

	@Bean
	GameScoreRepository gameScoreRepository() {
		return new JdbcGameScoreRepository(jdbcTemplate, objectSerializer());
	}
	
	private ObjectSerializer objectSerializer() {
		return new JSONObjectSerializer(objectMapper);
	}

	@Bean
	GameApplicationService gameApplicationService() {
		return new GameApplicationService(gameRepository(), new SimpleWinChecker());
	}
	
	@Bean
	GameScoreApplicationService gameScoreApplicationService() {
		return new GameScoreApplicationService(gameScoreRepository(), new DefaultScoringStrategy());
	}

	@Bean
	GameResource gameResource() {
		return new GameResource(gameApplicationService(), gameScoreApplicationService());
	}	
	
	@Bean
	NotificationResource NotificationResource() {
		return new NotificationResource();
	}	
}