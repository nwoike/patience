package com.patience.klondike.domain.model.game.scorer;

import com.patience.klondike.domain.model.game.GameId;

public interface GameScorerRepository {

	GameId nextIdentity();

	void save(GameScorer game);
	
	GameScorer gameOfId(GameId gameId);
	
	
}
