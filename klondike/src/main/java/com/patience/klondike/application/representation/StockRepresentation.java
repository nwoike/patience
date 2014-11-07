package com.patience.klondike.application.representation;

import com.patience.klondike.domain.model.game.Stock;

public class StockRepresentation {

	private final int cardCount;
	
	private final boolean passLimitReached;	
	
	public StockRepresentation(Stock stock) {
		this.cardCount = stock.cardCount();
		this.passLimitReached = stock.passLimitReached();
	}
	
	public int getCardCount() {
		return cardCount;
	}
	
	public boolean isPassLimitReached() {
		return passLimitReached;
	}
}
