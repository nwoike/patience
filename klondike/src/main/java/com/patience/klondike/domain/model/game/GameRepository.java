package com.patience.klondike.domain.model.game;

public interface GameRepository {

	GameId nextIdentity();

	void save(Game game);
	
	Game gameOfId(GameId gameId);
	
}