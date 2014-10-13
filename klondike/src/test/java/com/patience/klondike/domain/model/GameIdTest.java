package com.patience.klondike.domain.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

public class GameIdTest {

	@Test
	public void validId() {
		String id = UUID.randomUUID().toString();
		GameId gameId = new GameId(id);
		
		assertThat(id, equalTo(gameId.id()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void invalidId() {
		new GameId("");
	}

}
