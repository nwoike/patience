package com.patience.klondike.domain.model.game.score;


public interface ScoringStrategy {

	long calculatePoints(TrackedMoves moves);
		
}