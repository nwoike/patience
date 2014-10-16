package com.patience.common.domain.model;

public enum PlayingCard {

	TwoOfClubs(Rank.Two, Suit.Clubs),
	TwoOfDiamonds(Rank.Two, Suit.Diamonds),
	TwoOfHearts(Rank.Two, Suit.Hearts),
	TwoOfSpades(Rank.Two, Suit.Spades),

	ThreeOfClubs(Rank.Three, Suit.Clubs),
	ThreeOfDiamonds(Rank.Three, Suit.Diamonds),
	ThreeOfHearts(Rank.Three, Suit.Hearts),
	ThreeOfSpades(Rank.Three, Suit.Spades),
	
	FourOfClubs(Rank.Four, Suit.Clubs),
	FourOfDiamonds(Rank.Four, Suit.Diamonds),
	FourOfHearts(Rank.Four, Suit.Hearts),
	FourOfSpades(Rank.Four, Suit.Spades),
	
	FiveOfClubs(Rank.Five, Suit.Clubs),
	FiveOfDiamonds(Rank.Five, Suit.Diamonds),
	FiveOfHearts(Rank.Five, Suit.Hearts),
	FiveOfSpades(Rank.Five, Suit.Spades),
	
	SixOfClubs(Rank.Six, Suit.Clubs),
	SixOfDiamonds(Rank.Six, Suit.Diamonds),
	SixOfHearts(Rank.Six, Suit.Hearts),
	SixOfSpades(Rank.Six, Suit.Spades),
	
	SevenOfClubs(Rank.Seven, Suit.Clubs),
	SevenOfDiamonds(Rank.Seven, Suit.Diamonds),
	SevenOfHearts(Rank.Seven, Suit.Hearts),
	SevenOfSpades(Rank.Seven, Suit.Spades),
	
	EightOfClubs(Rank.Eight, Suit.Clubs),
	EightOfDiamonds(Rank.Eight, Suit.Diamonds),
	EightOfHearts(Rank.Eight, Suit.Hearts),
	EightOfSpades(Rank.Eight, Suit.Spades),
	
	NineOfClubs(Rank.Nine, Suit.Clubs),
	NineOfDiamonds(Rank.Nine, Suit.Diamonds),
	NineOfHearts(Rank.Nine, Suit.Hearts),
	NineOfSpades(Rank.Nine, Suit.Spades),
	
	TenOfClubs(Rank.Ten, Suit.Clubs),
	TenOfDiamonds(Rank.Ten, Suit.Diamonds),
	TenOfHearts(Rank.Ten, Suit.Hearts),
	TenOfSpades(Rank.Ten, Suit.Spades),
	
	JackOfClubs(Rank.Jack, Suit.Clubs),
	JackOfDiamonds(Rank.Jack, Suit.Diamonds),
	JackOfHearts(Rank.Jack, Suit.Hearts),
	JackOfSpades(Rank.Jack, Suit.Spades),
	
	QueenOfClubs(Rank.Queen, Suit.Clubs),
	QueenOfDiamonds(Rank.Queen, Suit.Diamonds),
	QueenOfHearts(Rank.Queen, Suit.Hearts),
	QueenOfSpades(Rank.Queen, Suit.Spades),
	
	KingOfClubs(Rank.King, Suit.Clubs),
	KingOfDiamonds(Rank.King, Suit.Diamonds),
	KingOfHearts(Rank.King, Suit.Hearts),
	KingOfSpades(Rank.King, Suit.Spades),
	
	AceOfClubs(Rank.Ace, Suit.Clubs),
	AceOfDiamonds(Rank.Ace, Suit.Diamonds),
	AceOfHearts(Rank.Ace, Suit.Hearts),
	AceOfSpades(Rank.Ace, Suit.Spades);
	
	private Rank rank;
	
	private Suit suit;
	
	private PlayingCard(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}
	
	public Rank rank() {
		return rank;
	}
	
	public Suit suit() {
		return suit;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	public static PlayingCard of(Rank rank, Suit suit) {		
		return valueOf(rank.name() + "Of" + suit.name());
	}
}