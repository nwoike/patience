package com.patience.klondike.application.representation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Foundation;


public class FoundationRepresentation {
	
	private final String suit;
	
	private final List<String> ranks = newArrayList();
	
	public FoundationRepresentation(Foundation foundation) {		
		this.suit = foundation.suit() != null ? foundation.suit().name() : null;
		
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