package com.patience.common.domain.model.card;

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

	public boolean precedes(Rank other) {
		return other.ordinal() - ordinal() == 1;
	}

	public boolean follows(Rank other) {
		return ordinal() - other.ordinal() == 1;
	}

	public boolean higherThan(Rank other) {
		return ordinal() > other.ordinal();
	}
}
