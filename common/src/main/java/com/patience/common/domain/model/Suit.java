package com.patience.common.domain.model;

import java.util.EnumSet;

public enum Suit {

	Clubs,
	Diamonds,
	Hearts,
	Spades;
	
	private static EnumSet<Suit> red = EnumSet.of(Diamonds, Hearts);
	
	private static EnumSet<Suit> black = EnumSet.of(Clubs, Spades);
	
	public boolean alternatingColorOf(Suit other) {
		return red.contains(this) && black.contains(other)
			|| black.contains(this) && red.contains(other);
	}
}
