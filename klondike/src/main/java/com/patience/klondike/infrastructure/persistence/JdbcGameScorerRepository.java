package com.patience.klondike.infrastructure.persistence;

import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.scorer.GameScorer;
import com.patience.klondike.domain.model.game.scorer.GameScorerRepository;

public class JdbcGameScorerRepository implements GameScorerRepository {

	@Override
	public GameId nextIdentity() {
		return null;
	}

	@Override
	public void save(GameScorer game) {
		
	}

	@Override
	public GameScorer gameOfId(GameId gameId) {		
		return null;
	}

}
