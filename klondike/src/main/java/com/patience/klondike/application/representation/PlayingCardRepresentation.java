package com.patience.klondike.application.representation;

import com.patience.common.domain.model.PlayingCard;

public class PlayingCardRepresentation {

	private String rank;
	
	private String suit;
	
	public PlayingCardRepresentation(PlayingCard card) {
		this.rank = card.rank().name();
		this.suit = card.suit().name();
	}
	
	public String getRank() {
		return rank;
	}
	
	public String getSuit() {
		return suit;
	}
}