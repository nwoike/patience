package com.patience.klondike.infrastructure.persistence;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.patience.klondike.domain.model.Game;
import com.patience.klondike.domain.model.GameId;
import com.patience.klondike.domain.model.GameRepository;

public class InMemoryGameRepository implements GameRepository {
	
	private AtomicInteger counter = new AtomicInteger(1);
	
	private Map<GameId, Game> savedGames = newHashMap();
	
	@Override
	public void save(Game game) {
		this.savedGames.put(game.gameId(), game);
	}

	public Game gameOfId(GameId gameId) {
		return savedGames.get(gameId);
	}
	
	@Override
	public GameId nextIdentity() {
		return new GameId(String.valueOf(counter.getAndIncrement()));
	}
}