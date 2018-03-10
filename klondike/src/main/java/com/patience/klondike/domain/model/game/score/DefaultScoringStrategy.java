package com.patience.klondike.domain.model.game.score;

import org.springframework.stereotype.Component;

@Component
public class DefaultScoringStrategy implements ScoringStrategy {

	@Override
	public long calculatePoints(TrackedMoves trackedMoves) {
		long points = 0;
		
		for (Move move : trackedMoves.moves()) {
			points += determinePointsForMove(move.origin(), move.destination());
		}
		
		points += trackedMoves.flippedCards().size() * determinePointsForFlip();
		points += trackedMoves.recycleCount() * determinePointsForRecycle();
		
		return Math.max(points, 0);
	}
	
	private int determinePointsForMove(PileType origin, PileType destination) {
		
		if (PileType.Waste.equals(origin)) {
			if (PileType.Tableau.equals(destination)) {
				return 5;
				
			} else if (PileType.Foundation.equals(destination)) {
				return 10;
			}
		}

		if (PileType.Tableau.equals(origin) 
			&& PileType.Foundation.equals(destination)) {
				return 10;
		}
		
		if (PileType.Foundation.equals(origin)
			&& PileType.Tableau.equals(destination)) {
				return -15;
		}
		
		return 0;
	}
	
	private int determinePointsForFlip() {
		return 5;
	}

	private int determinePointsForRecycle() {		
		return -100;
	}
}