package com.patience.klondike.domain.model.game;

public class SimpleWinnableChecker implements WinnableChecker {

	@Override
	public boolean isWinnable(Game game) {
		if (!(game.stock().isEmpty() && game.waste().isEmpty())) {
			return false;
		}
		
		boolean allTableauPileCardsFlipped = true;
		
		for (TableauPile tableauPile : game.tableauPiles()) {
			if (tableauPile.unflippedCardCount() > 0) {
				allTableauPileCardsFlipped = false;
				break;
			}			
		}
		
		return allTableauPileCardsFlipped;
	}
}
