package com.patience.common.domain.model.card;

import static com.patience.common.domain.model.card.SuitColor.Black;
import static com.patience.common.domain.model.card.SuitColor.Red;


public enum Suit {

	Clubs(Black),
	Diamonds(Red),
	Hearts(Red),
	Spades(Black);
	
	private SuitColor color;
	
	private Suit(SuitColor color) {
		this.color = color;
	}
	
	public SuitColor color() {
		return color;
	}
}
