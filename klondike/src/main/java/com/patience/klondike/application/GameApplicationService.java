package com.patience.klondike.application;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.klondike.domain.model.Game;
import com.patience.klondike.domain.model.GameId;
import com.patience.klondike.domain.model.GameRepository;

public class GameApplicationService {

	private GameRepository gameRepository;
	
	public GameApplicationService(GameRepository gameRepository) {
		this.gameRepository = checkNotNull(gameRepository, "Game Repository must be provided.");
	}
	
	public String startGame() {
		GameId gameId = gameRepository.nextIdentity();
		
		Game game = new Game(gameId);
		gameRepository.save(game);
		
		return gameId.toString();
	}
}