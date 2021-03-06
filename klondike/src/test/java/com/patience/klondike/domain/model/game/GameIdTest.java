package com.patience.klondike.domain.model.game;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

public class GameIdTest {

	@Test
	public void validId() {
		UUID id = UUID.randomUUID();
		GameId gameId = new GameId(id);
		
		assertThat(gameId.id(), equalTo(id.toString()));
	}
	
	@Test
	public void symetricId() {
		UUID id = UUID.randomUUID();
		GameId gameId = new GameId(id);
		
		GameId copy = new GameId(UUID.fromString(gameId.id()));		
		assertThat(copy.id(), equalTo(id.toString()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidId() {
		new GameId("");
	}

}
