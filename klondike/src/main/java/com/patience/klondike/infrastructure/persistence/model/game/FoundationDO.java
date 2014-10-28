package com.patience.klondike.infrastructure.persistence.model.game;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Foundation;
import com.patience.klondike.infrastructure.persistence.model.PlayingCardDO;


public class FoundationDO {
	
	private int foundationId;
	
	private List<PlayingCardDO> cards = newArrayList();
	
	public FoundationDO() {		
	}
	
	public FoundationDO(Foundation foundation) {	
		this.foundationId = foundation.foundationId();
		
		for (PlayingCard playingCard : foundation.cards()) {
			this.cards.add(new PlayingCardDO(playingCard));
		}	
	}
	
	public int getFoundationId() {
		return foundationId;
	}
	
	public void setFoundationId(int foundationId) {
		this.foundationId = foundationId;
	}
	
	public void setPlayingCards(List<PlayingCardDO> playingCards) {
		this.cards = playingCards;
	}
	
	public List<PlayingCardDO> getPlayingCards() {
		return cards;
	}	
	
	public Foundation toFoundation() {
		List<PlayingCard> playingCards = newArrayList();
		
		for (PlayingCardDO playingCardDO : cards) {
			playingCards.add(playingCardDO.toPlayingCard());
		}
		
		return new Foundation(foundationId, playingCards);
	}
}