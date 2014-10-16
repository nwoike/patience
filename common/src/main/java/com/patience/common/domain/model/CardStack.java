package com.patience.common.domain.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterators;

public final class CardStack implements Iterable<PlayingCard> {

	private List<PlayingCard> cards = newArrayList();
	
	private static final CardStack EMPTY = new CardStack();
	
	public CardStack(PlayingCard card) {
		this(newArrayList(card));
	}

	public CardStack(List<PlayingCard> cards) {
		checkNotNull(cards, "Playing cards must be provided.");
		checkArgument(!cards.isEmpty(), "At least one playing card must be provided to create a stack.");
		checkArgument(!cards.contains(null), "Playing cards should not contain null.");
		initializeStack(cards);
	}
	
	private CardStack() {		
	}
	
	public static final CardStack emptyStack() {
		return EMPTY;
	}
	
	private void initializeStack(List<PlayingCard> cards) {
		PlayingCard lastCard = null;
		
		for (PlayingCard card : cards) {
			if (lastCard == null) {
				this.cards.add(card);
				
			} else {
				if (!card.suit().alternatingColorOf(lastCard.suit())) {
					throw new IllegalArgumentException("Provided cards do not form a valid stack. " + lastCard + " and " + card + " are not of alternating suits.");
					
				} else if (!lastCard.rank().follows(card.rank())) {
					throw new IllegalArgumentException("Provided cards do not form a valid stack. " + lastCard + " and " + card + " are not in decreasing rank.");
				}
				
				this.cards.add(card);
			}
			
			lastCard = card;
		}
	}

	public CardStack withAdditionalCards(CardStack other) {
		return withAdditionalCards(other.cards);
	}

	public CardStack withAdditionalCards(List<PlayingCard> additionalCards) {
		List<PlayingCard> copy = newArrayList(cards);
		copy.addAll(additionalCards);
		
		return new CardStack(copy);
	}
	
	public CardStack withAdditionalCards(PlayingCard... additionalCards) {
		List<PlayingCard> copy = newArrayList(cards);
		copy.addAll(Arrays.asList(additionalCards));
		
		return new CardStack(copy);
	}
	
	public CardStack withCardsRemoved(CardStack other) {
		List<PlayingCard> copy = newArrayList(cards);
		
		if (!(topCard().equals(other.topCard()) && contains(other))) {
			throw new IllegalArgumentException("Provided CardStack is not removable.");
		}
		
		copy.removeAll(other.cards());
		
		if (copy.isEmpty()) {
			return emptyStack();
		}
		
		return new CardStack(copy);
	}
	
	public CardStack withCardsRemoved(List<PlayingCard> cards) {
		return withCardsRemoved(new CardStack(cards));
	}
	
	public CardStack withCardsRemoved(PlayingCard... cards) {
		return withCardsRemoved(Arrays.asList(cards));
	}
	
	public boolean contains(CardStack other) {
		return cards.containsAll(other.cards);
	}

	/**
	 * The card in a pile which doesn't have any cards on top of it.
	 */
	public PlayingCard topCard() {
		return cards.isEmpty() ? null : cards.get(cards.size() - 1);		
	}
	
	public PlayingCard bottomCard() {
		return cards.get(0);
	}
	
	public List<PlayingCard> cards() {		
		return newArrayList(cards);
	}
	
	public int cardCount() {
		return cards.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj != null && this.getClass() == obj.getClass()) {
			CardStack other = (CardStack) obj;
			return cards.equals(other.cards);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 991 * cards.hashCode();
	}

	public Iterator<PlayingCard> iterator() {
		return Iterators.unmodifiableIterator(cards.iterator());
	}
}
