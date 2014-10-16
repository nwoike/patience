package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;

public class Waste {

	private List<PlayingCard> cards = newArrayList();
	
	public Waste() {		
	}
	
	public void addCard(PlayingCard card) {
		checkNotNull(card, "Playing card must be provided.");		
		this.cards.add(card);
	}
	
	public PlayingCard removeTopCard() {
		if (cards.isEmpty()) {
			throw new IllegalStateException("The waste is currently empty. There are no cards to remove.");
		}
		
		return this.cards.remove(this.cards.size() - 1);
	}
	
	public PlayingCard topCard() {
		return cards.isEmpty() ? null : cards.get(cards.size() - 1);
	}
	
	public List<PlayingCard> cards() {
		return newArrayList(cards);
	}
	
	public void clear() {
		this.cards.clear();
	}
}