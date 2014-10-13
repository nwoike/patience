package com.patience.klondike.infrastructure.persistence;

import java.util.UUID;

import com.patience.klondike.domain.model.Game;
import com.patience.klondike.domain.model.GameId;
import com.patience.klondike.domain.model.GameRepository;

public class JdbcGameRepository implements GameRepository {

	@Override
	public GameId nextIdentity() {		
		return new GameId(UUID.randomUUID().toString());
	}

	@Override
	public void save(Game game) {
				
	}

	@Override
	public Game gameOfId(GameId gameId) {
		return null;
	}	
}