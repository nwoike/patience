package com.patience.klondike.application;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.patience.klondike.domain.service.game.SimpleWinChecker;
import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;

public class GameApplicationServiceTest {

	private GameApplicationService gameApplicationService;
	
	private InMemoryGameRepository gameRepository;
	
	@Before
	public void setup() {
		gameRepository = new InMemoryGameRepository();
		gameApplicationService = new GameApplicationService(gameRepository, new SimpleWinChecker());		
	}
	
	@Test
	public void createGame() {
		String newGameId = gameApplicationService.startGame("One", "Unlimited");
		assertThat(newGameId, equalTo("d5a6b733-5ed5-4a9e-9af3-5cd18e7ec1cb"));
	}
}