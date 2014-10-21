package com.patience.klondike.domain.service.game;

import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.TableauPile;

public class SimpleWinChecker implements WinChecker {

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
