package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;
import com.patience.common.domain.model.Rank;
import com.patience.common.domain.model.Suit;

public class Foundation {

	private Suit suit;

	// TODO: Could this be another type of card stack?
	private List<PlayingCard> cards = newArrayList();
	
	public Foundation(Suit suit) {
		this.suit = checkNotNull(suit, "Suit must be provided");
	}

	// TODO: Could this be another type of card stack?
	public void addCard(PlayingCard playingCard) {
		if (playingCard.suit() != suit) {
			throw new IllegalArgumentException("Provided card does not match the suit of this foundation.");
		}
		
		if (cards.isEmpty() && playingCard.rank() != Rank.Ace) {
			throw new IllegalArgumentException("First card in Foundation must be an Ace.");
		}
		
		PlayingCard topCard = topCard();
		
		if (topCard != null) {
			if (!playingCard.rank().follows(topCard.rank())) {
				throw new IllegalArgumentException("Provided card is not the next card in the Foundation sequence.");
			}
		}
		
		this.cards.add(playingCard);
	}
	
	public Suit suit() {
		return suit;
	}
	
	public PlayingCard topCard() {
		return (this.cards.isEmpty()) ? null : this.cards.get(this.cards.size() - 1);		
	}
	
	public List<PlayingCard> cards() {		
		return newArrayList(cards);
	}
}