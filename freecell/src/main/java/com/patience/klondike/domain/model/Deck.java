package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.patience.common.domain.model.card.PlayingCard;

public final class Deck {

	private final List<PlayingCard> cards = newArrayList();
	
	public Deck() {
		for (PlayingCard card : PlayingCard.values()) {
			this.cards.add(card);
		}
	}
	
	public Deck(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");
		this.cards().addAll(cards);
	}
	
	public PlayingCard drawCard() {
		if (cards.isEmpty()) {
			throw new IllegalArgumentException("Deck is empty.");
		}
		
		return cards.remove(cards.size() - 1);
	}
	
	public void shuffle(long seed) {
		Random random = new Random(seed);
		Collections.shuffle(cards, random);
	}
	
	public int remainingCardCount() {
		return cards.size();
	}
	
	protected List<PlayingCard> cards() {
		return newArrayList(cards);
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	@Override
	public String toString() {
		return cards.toString();
	}
}