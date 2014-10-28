package com.patience.klondike.domain.model.game;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.score.PileType;

interface Pile {
		
	boolean topCardsMatch(List<PlayingCard> cards);
	
	PlayingCard removeTopCard();
	
	void removeCards(List<PlayingCard> cards);
	
	PileType pileType();
	
}
