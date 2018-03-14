package com.patience.klondike.domain.service.game;

import org.springframework.stereotype.Component;

import com.patience.klondike.domain.model.game.Game;

@Component
public class SimpleWinChecker implements WinChecker {

	@Override
	public boolean isWinnable(Game game) {
		if (!(game.stock().isEmpty() && game.waste().isEmpty())) {
			return false;
		}
		
		return game.tableauPiles().stream()
		    .allMatch(pile -> pile.unflippedCardCount() == 0);
	}
}
