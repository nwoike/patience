package com.patience.klondike.infrastructure.persistence.model.game.score;

import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.score.GameScore;

public class GameScoreDO {

	private String gameId;
	
	private TrackedMovesDO trackedMoves;
	
	public GameScoreDO() {
	}
	
	public GameScoreDO(GameScore gameScore) {
		this.gameId = gameScore.gameId().id();
		this.trackedMoves = new TrackedMovesDO(gameScore.trackedMoves());
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public void setTrackedMovesDO(TrackedMovesDO trackedMoves) {
		this.trackedMoves = trackedMoves;
	}
	
	public TrackedMovesDO getTrackedMovesDO() {
		return trackedMoves;
	}
	
	public GameScore toGameScore() {
		return new GameScore(new GameId(gameId), trackedMoves.toTrackedMoves());
	}	
}
