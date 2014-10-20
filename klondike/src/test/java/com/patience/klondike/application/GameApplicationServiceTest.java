package com.patience.klondike.application;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.patience.klondike.domain.model.game.SimpleWinnableChecker;
import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;

public class GameApplicationServiceTest {

	private GameApplicationService gameApplicationService;
	
	private InMemoryGameRepository gameRepository;
	
	@Before
	public void setup() {
		gameRepository = new InMemoryGameRepository();
		gameApplicationService = new GameApplicationService(gameRepository, new SimpleWinnableChecker());		
	}
	
	@Test
	public void createGame() {
		String newGameId = gameApplicationService.startGame();
		assertThat(newGameId, equalTo("1"));
	}

}