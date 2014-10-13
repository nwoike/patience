package com.patience.klondike.application.representation;

import com.patience.klondike.domain.model.Stock;

public class StockRepresentation {

	private int cardCount;
	
	private int passes;
	
	public StockRepresentation(Stock stock) {
		
	}
	
	public int getCardCount() {
		return cardCount;
	}
	
	public int getPasses() {
		return passes;
	}
}
