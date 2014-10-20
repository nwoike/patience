package com.patience.klondike.application.representation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.TableauPile;

public class TableauPileRepresentation {

	private int cardCount;

	private List<PlayingCardRepresentation> flipped = newArrayList();
	
	public TableauPileRepresentation(TableauPile tableauPile) {
		this.cardCount = tableauPile.totalCardCount();
		
		for (PlayingCard card : tableauPile.flippedCards()) {
			this.flipped.add(new PlayingCardRepresentation(card));
		}
	}

	public int getCardCount() {
		return cardCount;
	}
	
	public List<PlayingCardRepresentation> getFlipped() {
		return flipped;
	}
}