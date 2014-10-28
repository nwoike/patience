package com.patience.klondike.application;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.transaction.annotation.Transactional;

import com.patience.klondike.application.representation.PlayingCardRepresentation;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.score.GameScore;
import com.patience.klondike.domain.model.game.score.GameScoreRepository;
import com.patience.klondike.domain.model.game.score.PileType;
import com.patience.klondike.domain.model.game.score.ScoringStrategy;

@Transactional
public class GameScoreApplicationService {

	private GameScoreRepository repository;
	
	private ScoringStrategy scoringStrategy;
	
	public GameScoreApplicationService(GameScoreRepository repository, ScoringStrategy scoringStrategy) {
		this.repository = checkNotNull(repository, "GameScorerRepository is required.");
		this.scoringStrategy = checkNotNull(scoringStrategy, "ScoringStrategy must be provided.");
	}
	
	public long retrieveGameScore(String gameId) {
		GameScore gameScore = gameScoreOf(gameId);		
		return gameScore.calculateScore(scoringStrategy);
	}
	
	public void createGameScore(String gameId) {
		GameScore gameScore = new GameScore(new GameId(gameId));
		repository.save(gameScore);
	}
	
	public void scoreCardMove(String gameId, PlayingCardRepresentation card, String origin, String destination) {
		GameScore gameScore = gameScoreOf(gameId);		
		gameScore.cardMoved(card.toPlayingCard(), PileType.valueOf(origin), PileType.valueOf(destination));
		
		repository.save(gameScore);
	}
	
	public void scoreCardFlip(String gameId, PlayingCardRepresentation card) {
		GameScore gameScore = gameScoreOf(gameId);		
		gameScore.cardFlipped(card.toPlayingCard());
		
		repository.save(gameScore);
	}
	
	public void scoreStockRecycle(String gameId) {
		GameScore gameScore = gameScoreOf(gameId);		
		gameScore.stockRecycled();
		
		repository.save(gameScore);
	}

	private GameScore gameScoreOf(String gameId) {
		GameScore gameScore = repository.gameScoreOfId(new GameId(gameId));
		
		if (gameScore == null) {
			throw new IllegalArgumentException("GameScore was not found.");
		}
		
		return gameScore;
	}
}
