package com.patience.klondike.application;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;

public class GameApplicationServiceTest {

	private GameApplicationService service;
	
	private InMemoryGameRepository gameRepository;
	
	@Before
	public void setup() {
		gameRepository = new InMemoryGameRepository();
		service = new GameApplicationService(gameRepository);		
	}
	
	@Test
	public void createGame() {
		String newGameId = service.startGame();
		assertThat(newGameId, equalTo("1"));
	}

}