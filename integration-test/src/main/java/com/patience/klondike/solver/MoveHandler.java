package com.patience.klondike.solver;

import com.patience.common.domain.model.card.PlayingCard;

public interface MoveHandler {

	void promoteCard(PlayingCard card);
	
	void flipCard(int tableauPileId);
	
	void moveCards(PlayingCard bottomCard, int tableauPileId);

	void drawCard();

}