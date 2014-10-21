package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.AnyOrder;
import com.patience.common.specification.CardStackingStyle;

public class Waste {

	private CardStack stack;
	
	private static CardStackingStyle stackingStyle = new AnyOrder();
	
	public Waste() {
		this.stack = new CardStack(stackingStyle);
	}
	
	public Waste(List<PlayingCard> playingCards) {
		checkNotNull(playingCards, "Playing cards must be provided.");
		this.stack = new CardStack(playingCards);
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
		this.stack = stack.withCardRemoved(topCard);
		
		return topCard;
	}
	
	public PlayingCard topCard() {
		return stack.topCard();
	}
	
	public boolean topCardMatches(PlayingCard other) {
		return other.equals(topCard());
	}
	
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	public List<PlayingCard> cards() {
		return stack.cards();
	}
	
	public void clear() {
		this.stack = new CardStack();
	}
}