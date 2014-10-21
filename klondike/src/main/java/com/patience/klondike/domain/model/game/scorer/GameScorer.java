package com.patience.klondike.domain.model.game.scorer;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.Entity;
import com.patience.klondike.domain.model.game.GameId;

public class GameScorer extends Entity {

	private final GameId gameId;
	
	private int wasteToTableau;
	
	private int tableauToFoundation;
	
	private int foundationToTableau;

	private int tableauCardFlipped;
	
	private int wasteRecycled;
	
	public GameScorer(GameId gameId) {
		this.gameId = gameId;
	}
	
	public void cardMoved(PlayingCard card, PileType source, PileType destination) {
		
	}
	
	public void cardFlipped(PlayingCard card) {
		
	}
	
	public void stockRecycled() {
		
	}
	
	public int calculateScore(ScoringStrategy scoringStrategy) {
		return 0;
	}
	
	public GameId gameId() {
		return gameId;
	}
}
