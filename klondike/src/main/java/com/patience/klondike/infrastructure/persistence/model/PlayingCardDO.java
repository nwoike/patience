package com.patience.klondike.infrastructure.persistence.model;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.card.Suit;

public class PlayingCardDO {

	private String rank;
	
	private String suit;
	
	public PlayingCardDO() {
	}
	
	public PlayingCardDO(PlayingCard card) {
		this.rank = card.rank().name();
		this.suit = card.suit().name();
	}

	public String getRank() {
		return rank;
	}
	
	public void setRank(String rank) {
		this.rank = rank;
	}
		
	public String getSuit() {
		return suit;
	}
	
	public void setSuit(String suit) {
		this.suit = suit;
	}
	
	public PlayingCard toPlayingCard() {
		return PlayingCard.of(Rank.valueOf(getRank()), Suit.valueOf(getSuit()));
	}
}