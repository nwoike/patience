package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;

public class Stock {

	private List<PlayingCard> cards = newArrayList();
	
	public Stock(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");		
		this.cards.addAll(cards);
	}
	
	public PlayingCard drawCard() throws StockEmptyException {
		if (cards.isEmpty()) {
			throw new StockEmptyException();
		}
		
		return cards.remove(cards.size() - 1);
	}
	
	public void restock(List<PlayingCard> cards) {
		checkNotNull(cards, "Provided restock cards must be provided.");
		this.cards.addAll(cards);
	}
	
	public int cardCount() {
		return cards.size();
	}
}
