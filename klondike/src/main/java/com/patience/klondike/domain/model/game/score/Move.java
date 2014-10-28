package com.patience.klondike.domain.model.game.score;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.PlayingCard;

public final class Move {

	private final PlayingCard playingCard;
	
	private final PileType origin;
	
	private final PileType destination;
	
	public Move(PlayingCard playingCard, PileType origin, PileType destination) {
		this.playingCard = checkNotNull(playingCard, "PlayingCard must be provided.");
		this.origin = checkNotNull(origin, "Origin must be provided.");
		this.destination = checkNotNull(destination, "Destination must be provided.");		
	}
	
	public PileType destination() {
		return destination;
	}
	
	public PileType origin() {
		return origin;
	}
	
	public PlayingCard playingCard() {
		return playingCard;
	}
	
	@Override
	public int hashCode() {		
		return 239 * origin.hashCode() + destination.hashCode() + playingCard.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {	
		if (obj != null && getClass().equals(obj.getClass())) {
			Move other = (Move) obj;
			
			return playingCard.equals(other.playingCard())
				&& origin.equals(other.origin())
				&& destination.equals(other.destination());
		}
		
		return false;
	}
}