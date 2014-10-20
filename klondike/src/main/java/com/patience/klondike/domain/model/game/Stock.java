package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.AnyOrder;
import com.patience.common.specification.CardStackingStyle;

public class Stock {

	private CardStack cardStack;
	
	private static CardStackingStyle stackingStyle = new AnyOrder();
	
	public Stock(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");		
		this.cardStack = new CardStack(cards, stackingStyle);
	}
	
	public PlayingCard drawCard() throws EmptyStockException {
		if (cardStack.isEmpty()) {
			throw new EmptyStockException();
		}
		
		PlayingCard topCard = cardStack.topCard();
		this.cardStack = cardStack.withCardRemoved(topCard);
		
		return topCard;
	}
	
	public void restock(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");
		this.cardStack = new CardStack(cards);
	}
	
	public List<PlayingCard> playingCards() {
		return newArrayList(cardStack);
	}
	
	public int cardCount() {
		return cardStack.cardCount();
	}
	
	public boolean isEmpty() {
		return cardStack.isEmpty();
	}
}