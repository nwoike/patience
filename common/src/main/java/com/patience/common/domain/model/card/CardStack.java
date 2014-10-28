package com.patience.common.domain.model.card;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Iterators;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;
import com.patience.common.domain.model.cardstack.style.StackingStyleTracker;

public final class CardStack implements Iterable<PlayingCard> {

	private final List<PlayingCard> cards = newArrayList();
	
	private CardStackingStyle stackingStyle;
	
	public CardStack() {		
	}

	public CardStack(CardStackingStyle stackingStyle) {
		checkNotNull(stackingStyle, "CardStackingStyle must be provided.");
		this.stackingStyle = stackingStyle;		
	}
	
	public CardStack(CardStack cardStack, CardStackingStyle stackingStyle) {
		checkNotNull(stackingStyle, "CardStackingStyle must be provided.");
		this.stackingStyle = stackingStyle;	
		List<PlayingCard> cards = cardStack.cards();
		validateCards(cards);
		this.cards.addAll(cards);
	}
	
	public CardStack(PlayingCard... card) {		
		this(Arrays.asList(card));
	}	
	
	public CardStack(List<PlayingCard> cards) {		
		validateCards(cards);
		this.cards.addAll(cards);	
	}
	
	public CardStack(List<PlayingCard> cards, CardStackingStyle stackingStyle) {
		this(stackingStyle);
		validateCards(cards);
		this.cards.addAll(cards);	
	}
	
	public CardStack withAdditionalCard(PlayingCard card) {
		return withAdditionalCards(newArrayList(card));
	}
	
	public CardStack withAdditionalCards(CardStack cards) {
		return withAdditionalCards(cards.cards());
	}
	
	public CardStack withAdditionalCards(List<PlayingCard> cards) {
		List<PlayingCard> copy = newArrayList(this.cards);
		copy.addAll(cards);
		
		if (stackingStyle != null) {
			return new CardStack(copy, stackingStyle);
			
		} else {
			return new CardStack(copy);
		}
	}

	public CardStack withCardsRemoved(List<PlayingCard> remove) {
		return withCardsRemoved(new CardStack(remove));
	}

	public CardStack withCardRemoved(PlayingCard remove) {
		return withCardsRemoved(new CardStack(remove));
	}
	
	/**
	 * Cards can currently only be removed from the top of the stack.
	 */
	public CardStack withCardsRemoved(CardStack other) {
		checkNotNull(other, "CardStack must be provided.");
		
		if (!endsWith(other)) {
			throw new IllegalArgumentException("Provided CardStack is not removable.");
		}
		
		LinkedList<PlayingCard> copy = newLinkedList(cards);
		copy.removeAll(other.cards());
		
		if (stackingStyle != null) {
			return new CardStack(copy, stackingStyle);
			
		} else {
			return new CardStack(copy);
		}
	}
	
	public boolean contains(CardStack cardStack) {		
		return contains(cardStack.cards());
	}
	
	public boolean contains(List<PlayingCard> cards) {
		return cards.containsAll(cards);
	}

	public boolean endsWith(List<PlayingCard> cards) {
		return endsWith(new CardStack(cards));		
	}
	
	public boolean endsWith(CardStack other) {
		checkNotNull(other, "CardStack must be provided.");
		
		if (other.cardCount() > cardCount()) {
			return false;
		}
		
		List<PlayingCard> copy = cards();
		Collections.reverse(copy);
		
		List<PlayingCard> otherPlayingCards = other.cards();
		Collections.reverse(otherPlayingCards);
		
		for (int i = 0; i < otherPlayingCards.size(); i++) {
			PlayingCard card = copy.get(i);
			PlayingCard otherCard = otherPlayingCards.get(i);
			
			if (!card.equals(otherCard)) {
				return false;
			}
		}
		
		return true;
	}
	
	public PlayingCard bottomCard() {
		return cards.isEmpty() ? null : cards.get(0);
	}
	
	public PlayingCard topCard() {
		return cards.isEmpty() ? null : cards.get(cards.size() - 1);
	}
	
	public List<PlayingCard> cards() {
		return newArrayList(cards);
	}	
	
	public int cardCount() {
		return cards.size();
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	private void validateCards(List<PlayingCard> cards) {
		checkNotNull(cards, "Playing cards must be provided.");	
		checkArgument(!cards.contains(null), "Playing card list should not contain null.");
				
		if (stackingStyle != null) {
			CardStack copy = new CardStack(cards);		
			StackingStyleTracker tracker = new StackingStyleTracker();
			
			if (!stackingStyle.isSatisfiedBy(copy, tracker)) {
				throw new IllegalArgumentException("Provided cards were invalid for the current stacking style. " + tracker.unsatisfiedStackingStyles());
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			CardStack other = (CardStack) obj;
			return cards.equals(other.cards());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 991 * cards.hashCode();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}

	@Override
	public Iterator<PlayingCard> iterator() {		
		return Iterators.unmodifiableIterator(cards.iterator());
	}
}