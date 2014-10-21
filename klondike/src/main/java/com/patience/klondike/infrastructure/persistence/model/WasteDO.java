package com.patience.klondike.infrastructure.persistence.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Waste;

public class WasteDO {

	private List<PlayingCardDO> cards = newArrayList();

	public WasteDO() {
	}
	
	public WasteDO(Waste waste) {
		for (PlayingCard card : waste.cards()) {
			this.cards.add(new PlayingCardDO(card));
		}		
	}
	
	public void setCards(List<PlayingCardDO> cards) {
		this.cards = cards;
	}
	
	public List<PlayingCardDO> getCards() {		
		return cards;
	}
	
	public Waste toWaste() {
		List<PlayingCard> playingCards = newArrayList();
		
		for (PlayingCardDO playingCardDO : cards) {
			playingCards.add(playingCardDO.toPlayingCard());
		}
		
		return new Waste(playingCards);
	}
}
