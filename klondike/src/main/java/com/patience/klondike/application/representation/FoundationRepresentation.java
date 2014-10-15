package com.patience.klondike.application.representation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;
import com.patience.klondike.domain.model.Foundation;


public class FoundationRepresentation {

	private String suit;
	
	private List<String> ranks = newArrayList();
	
	public FoundationRepresentation(Foundation foundation) {		
		this.suit = foundation.suit().name();
		
		List<PlayingCard> cards = foundation.cards();
		
		for (PlayingCard playingCard : cards) {
			this.ranks.add(playingCard.rank().name());
		}		
	}
	
	public String getSuit() {
		return suit;
	}
	
	public List<String> getRanks() {
		return ranks;
	}
}