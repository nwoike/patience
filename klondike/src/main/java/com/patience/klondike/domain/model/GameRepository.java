package com.patience.klondike.domain.model;

public interface GameRepository {

	GameId nextIdentity();

	void save(Game game);
	
	Game gameOfId(GameId gameId);
	
}