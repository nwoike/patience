package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.Suit;

public class Foundation {

	private Suit suit;

	public Foundation(Suit suit) {
		this.suit = checkNotNull(suit, "Suit must be provided");

	}

	public Suit suit() {
		return suit;
	}
}