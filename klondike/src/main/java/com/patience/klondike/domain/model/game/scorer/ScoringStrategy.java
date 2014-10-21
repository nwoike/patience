package com.patience.klondike.domain.model.game.scorer;

import com.patience.common.domain.model.card.PlayingCard;

public interface ScoringStrategy {

	int determinePointsForMove(PlayingCard card, PileType source, PileType destination);

	int determinePointsForRecycle();
	
	int determinePointsForFlip(PlayingCard card);
	
}
