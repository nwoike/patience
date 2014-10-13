package com.patience.common.domain.model;

public enum Rank {

	Ace,
	Two,
	Three,
	Four,
	Five,
	Six,
	Seven,
	Eight,
	Nine,
	Ten,
	Jack,
	Queen,
	King;

	public boolean preceds(Rank other) {
		return other.ordinal() - ordinal() == 1;
	}

	public boolean follows(Rank other) {
		return ordinal() - other.ordinal() == 1;
	}
}
