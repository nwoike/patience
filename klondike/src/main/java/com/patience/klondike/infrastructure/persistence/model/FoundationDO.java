package com.patience.klondike.infrastructure.persistence.model;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Foundation;


public class FoundationDO {
	
	private int foundationId;
	
	private List<PlayingCard> playingCards;
	
	public FoundationDO() {		
	}
	
	public FoundationDO(Foundation foundation) {	
		this.foundationId = foundation.foundationId();
		this.playingCards = foundation.cards();
	}
	
	public int getFoundationId() {
		return foundationId;
	}
	
	public void setFoundationId(int foundationId) {
		this.foundationId = foundationId;
	}
	
	public void setPlayingCards(List<PlayingCard> playingCards) {
		this.playingCards = playingCards;
	}
	
	public List<PlayingCard> getPlayingCards() {
		return playingCards;
	}	
	
	public Foundation toFoundation() {
		return new Foundation(foundationId, playingCards);
	}
}