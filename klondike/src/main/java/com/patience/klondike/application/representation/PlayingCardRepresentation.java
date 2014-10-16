package com.patience.klondike.application.representation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.common.domain.model.PlayingCard;
import com.patience.common.domain.model.Rank;
import com.patience.common.domain.model.Suit;

public class PlayingCardRepresentation {

	private String rank;
	
	private String suit;
	
	public PlayingCardRepresentation(PlayingCard card) {
		this.rank = card.rank().name();
		this.suit = card.suit().name();
	}
	
	@JsonCreator
	public PlayingCardRepresentation(@JsonProperty("rank") String rank, @JsonProperty("suit") String suit) {
		this.rank = rank;
		this.suit = suit;	
	}
	
	public String getRank() {
		return rank;
	}
	
	public String getSuit() {
		return suit;
	}
	
	public PlayingCard toPlayingCard() {
		return PlayingCard.of(Rank.valueOf(getRank()), Suit.valueOf(getSuit()));
	}
}