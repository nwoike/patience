package com.patience.klondike.infrastructure.persistence;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.UUID;

import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.GameRepository;

/**
 * No thread safety.
 */
public class InMemoryGameRepository implements GameRepository {
		
	private Map<GameId, Game> savedGames = newHashMap();
	
	@Override
	public void save(Game game) {
		if (!savedGames.containsKey(game.gameId())) {
			this.savedGames.put(game.gameId(), game);
		}
	}

	public Game gameOfId(GameId gameId) {
		return savedGames.get(gameId);
	}
	
	@Override
	public GameId nextIdentity() {		
		UUID uuid = UUID.fromString("d5a6b733-5ed5-4a9e-9af3-5cd18e7ec1cb");
		return new GameId(uuid);
	}
	
	public void reset() {
		savedGames.clear();
	}
}