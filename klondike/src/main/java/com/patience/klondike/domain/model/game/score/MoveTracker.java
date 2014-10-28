package com.patience.klondike.domain.model.game.score;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.Set;

import com.patience.common.domain.model.card.PlayingCard;

public class MoveTracker {
	
	private Set<Move> moves = newLinkedHashSet();
	
	private Set<PlayingCard> flippedCards = newLinkedHashSet();
	
	public int recycleCount;

	MoveTracker() {
	}
	
	public MoveTracker(TrackedMoves trackedMoves) {
		checkNotNull(trackedMoves, "TrackedMoves must be provided.");
		
		this.moves.addAll(trackedMoves.moves());
		this.flippedCards.addAll(trackedMoves.flippedCards());
		this.recycleCount = trackedMoves.recycleCount();
	}
	
	public Set<PlayingCard> flippedCards() {
		return newLinkedHashSet(flippedCards);
	}
	
	public Set<Move> moves() {
		return newLinkedHashSet(moves);
	}
	
	public int recycleCount() {
		return recycleCount;
	}
	
	public void addMove(Move move) {
		this.moves.add(move);
	}
	
	public void cardFlipped(PlayingCard playingCard) {
		checkNotNull(playingCard, "Playing card must be provided.");
		this.flippedCards.add(playingCard);
	}

	public void stockRecycled() {
		recycleCount++;
	}
	
	public TrackedMoves toTrackedMoves() {
		return new TrackedMoves(moves(), flippedCards(), recycleCount);
	}
}