package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.AnyOrder;
import com.patience.common.domain.model.cardstack.CardStack;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;
import com.patience.klondike.application.IllegalMoveException;

public final class Stock {

	private CardStack cardStack;
	
	private static CardStackingStyle stackingStyle = new AnyOrder();
	
	private PassCount passCount;
	
	private int passes;
	
	public Stock(List<PlayingCard> cards, PassCount passCount) {
		this(cards, passCount, 0);
	}
	
	public Stock(List<PlayingCard> cards, PassCount passCount, int passes) {
		checkNotNull(cards, "Cards must be provided.");		
		this.cardStack = new CardStack(cards, stackingStyle);
		this.passCount = checkNotNull(passCount, "PassCount must be provided.");
		this.passes = passes;
	}
	
	public PlayingCard drawCard() {
		if (cardStack.isEmpty()) {
			return null;
		}
		
		PlayingCard topCard = cardStack.topCard();
		this.cardStack = cardStack.withCardRemoved(topCard);
		
		if (this.cardStack.isEmpty()) {
			passes++;
		}
		
		return topCard;
	}
	
	public void recycle(Waste waste) {
		checkNotNull(waste, "Waste must be provided.");
		
		if (passCount.limitReached(passes)) {
			throw new IllegalMoveException("Draw limit has been reached.");
		}
		
		List<PlayingCard> wasted = waste.cards();
		Collections.reverse(wasted);
		waste.clear();
		
		this.cardStack = new CardStack(wasted);
	}
	
	public int passes() {
		return passes;
	}
	
	public boolean passLimitReached() {
		return passCount.limitReached(passes);
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