package com.patience.klondike.domain.model.game.score;

import com.patience.klondike.domain.model.game.GameId;

public interface GameScoreRepository {

	void save(GameScore game);
	
	GameScore gameScoreOfId(GameId gameId);
	
	
}
