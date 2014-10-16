package com.patience.klondike.resource.request;

import java.util.List;

import com.patience.klondike.application.representation.PlayingCardRepresentation;

public class MoveCards {

	private int tableauPileId;
	
	private List<PlayingCardRepresentation> cards;
	
	public List<PlayingCardRepresentation> getCards() {
		return cards;
	}
	
	public int getTableauPileId() {
		return tableauPileId;
	}
	
	public void setCards(List<PlayingCardRepresentation> cards) {
		this.cards = cards;
	}
	
	public void setTableauPileId(int tableauPileId) {
		this.tableauPileId = tableauPileId;
	}
}
