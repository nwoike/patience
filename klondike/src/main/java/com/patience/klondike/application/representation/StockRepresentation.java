package com.patience.klondike.application.representation;

import com.patience.klondike.domain.model.game.Stock;

public class StockRepresentation {

	private int cardCount;
	
	public StockRepresentation(Stock stock) {
		this.cardCount = stock.cardCount();
	}
	
	public int getCardCount() {
		return cardCount;
	}
}
