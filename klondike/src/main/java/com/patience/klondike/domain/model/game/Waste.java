package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.AnyOrder;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.domain.model.game.score.PileType;

public final class Waste implements Pile {

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
	
	@Override
	public void removeCards(List<PlayingCard> cards) {
		if (cards.size() > 1) {
			throw new IllegalMoveException("Can only remove a single card from the Waste at a time.");
		}
		
		removeTopCard();
	}
	
	public PlayingCard removeTopCard() {
		if (stack.isEmpty()) {
			throw new IllegalMoveException("The Waste is currently empty. There are no cards to remove.");
		}
		
		PlayingCard topCard = stack.topCard();
		this.stack = stack.withCardRemoved(topCard);
		
		return topCard;
	}
	
	public PlayingCard topCard() {
		return stack.topCard();
	}
	
	public boolean topCardsMatch(List<PlayingCard> cards) {
		return stack.endsWith(cards);
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
	
	@Override
	public PileType pileType() {
		return PileType.Waste;
	}

	public int cardCount() {		
		return stack.cardCount();
	}
}