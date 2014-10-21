package com.patience.klondike.domain.model.game.scorer;

import com.patience.common.domain.model.card.PlayingCard;

public class DefaultScoringStrategy implements ScoringStrategy {

	@Override
	public int determinePointsForMove(PlayingCard card, PileType source, PileType destination) {
		
		if (PileType.Waste.equals(source)) {
			if (PileType.Tableau.equals(destination)) {
				return 5;
				
			} else if (PileType.Foundation.equals(destination)) {
				return 10;
			}
		}

		if (PileType.Tableau.equals(source) 
			&& PileType.Foundation.equals(destination)) {
				return 10;
		}
		
		if (PileType.Foundation.equals(source)
			&& PileType.Tableau.equals(destination)) {
				return -15;
		}
		
		return 0;
	}
	
	@Override
	public int determinePointsForFlip(PlayingCard card) {
		return 5;
	}
	
	@Override
	public int determinePointsForRecycle() {		
		return -100;
	}
}
