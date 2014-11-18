package com.patience.klondike.solver;

import com.google.common.base.MoreObjects;
import com.patience.common.domain.model.card.PlayingCard;

public class PromoteCard extends Move {

	private PlayingCard card;

	public PromoteCard(PlayingCard playingCard) {
		this.card = playingCard;		
	}

	public PlayingCard card() {
		return card;
	}
	
	@Override
	public void play(MoveHandler handler) {
		handler.promoteCard(card);		
	}
	
	@Override
	protected int priority() {
		return 2;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			PromoteCard other = (PromoteCard) obj;
			return card.equals(other.card());
		}
			
		return false;
	}
	
	@Override
	public int hashCode() {
		return 499 * card.hashCode();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("card", card)
			.toString();
	}
}