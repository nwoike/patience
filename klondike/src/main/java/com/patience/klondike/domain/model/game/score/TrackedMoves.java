package com.patience.klondike.domain.model.game.score;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Set;

import com.patience.common.domain.model.card.PlayingCard;

public class TrackedMoves {

	private final Set<Move> moves;
	
	private final Set<PlayingCard> flippedCards;
	
	public final int recycleCount;
	
	public TrackedMoves(Set<Move> moves, Set<PlayingCard> flippedCards, int recycleCount) {
		this.moves = checkNotNull(moves, "Moves must be provided.");
		this.flippedCards = checkNotNull(flippedCards, "Flipped cards must be provided.");
		this.recycleCount = recycleCount;		
	}

	public Set<PlayingCard> flippedCards() {
		return flippedCards;
	}
	
	public Set<Move> moves() {
		return moves;
	}
	
	public int recycleCount() {
		return recycleCount;
	}
}
