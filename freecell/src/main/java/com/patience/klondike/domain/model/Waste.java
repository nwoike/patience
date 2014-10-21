package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;

public class Waste {

	private CardStack stack;
	
	public Waste() {		
		this.stack = new CardStack();
	}
	
	public void addCard(PlayingCard card) {
		checkNotNull(card, "Playing card must be provided.");		
		this.stack = stack.withAdditionalCard(card);
	}
	
	public PlayingCard removeTopCard() {
		if (stack.isEmpty()) {
			throw new IllegalStateException("The waste is currently empty. There are no cards to remove.");
		}
		
		PlayingCard topCard = stack.topCard();
		this.stack = stack.withCardsRemove(topCard);
		
		return topCard;
	}
	
	public PlayingCard topCard() {
		return stack.topCard();
	}
	
	public List<PlayingCard> playingCards() {
		return stack.playingCards();
	}
	
	public void clear() {
		this.stack = new CardStack();
	}
}