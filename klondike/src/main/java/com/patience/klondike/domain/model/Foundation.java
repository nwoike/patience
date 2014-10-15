package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;
import com.patience.common.domain.model.Suit;

public class Foundation {

	private Suit suit;

	private List<PlayingCard> cards = newArrayList();
	
	public Foundation(Suit suit) {
		this.suit = checkNotNull(suit, "Suit must be provided");
	}

	public Suit suit() {
		return suit;
	}
	
	public List<PlayingCard> cards() {		
		return newArrayList(cards);
	}
}