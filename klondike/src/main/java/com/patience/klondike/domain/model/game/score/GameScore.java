package com.patience.klondike.domain.model.game.score;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.Entity;
import com.patience.klondike.domain.model.game.GameId;

public class GameScore extends Entity {

	private final GameId gameId;
	
	private MoveTracker moveTracker;
	
	public GameScore(GameId gameId) {
		this.gameId = gameId;
		this.moveTracker = new MoveTracker();
	}
	
	public GameScore(GameId gameId, TrackedMoves trackedMoves) {
		this.gameId = gameId;
		this.moveTracker = new MoveTracker(trackedMoves);
	}
	
	public void cardMoved(PlayingCard card, PileType origin, PileType destination) {
		moveTracker.addMove(new Move(card, origin, destination));
	}
	
	public void cardFlipped(PlayingCard card) {
		moveTracker.cardFlipped(card);
	}
	
	public void stockRecycled() {
		moveTracker.stockRecycled();
	}
	
	public long calculateScore(ScoringStrategy scoringStrategy) {		
		return scoringStrategy.calculatePoints(moveTracker.toTrackedMoves());
	}
	
	public GameId gameId() {
		return gameId;
	}
	
	public TrackedMoves trackedMoves() {
		return moveTracker.toTrackedMoves();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			GameScore other = (GameScore) obj;
			return gameId.equals(other.gameId());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 409 * gameId.hashCode();
	}
}
