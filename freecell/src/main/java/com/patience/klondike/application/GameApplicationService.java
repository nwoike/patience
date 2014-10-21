package com.patience.klondike.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.application.representation.GameRepresentation;
import com.patience.klondike.application.representation.PlayingCardRepresentation;
import com.patience.klondike.domain.model.Game;
import com.patience.klondike.domain.model.GameId;
import com.patience.klondike.domain.model.GameRepository;

public class GameApplicationService {

	private GameRepository gameRepository;
	
	public GameApplicationService(GameRepository gameRepository) {
		this.gameRepository = checkNotNull(gameRepository, "Game Repository must be provided.");
	}
		
	public GameRepresentation retrieveGame(String gameId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}
		
		return new GameRepresentation(game);
	}
	
	public String startGame() {
		GameId gameId = gameRepository.nextIdentity();
		
		Game game = new Game(gameId);
		gameRepository.save(game);
		
		return gameId.toString();
	}
	
	public void drawCard(String gameId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}
		
		game.drawCard();
	}
	
	public void moveCards(String gameId, List<PlayingCardRepresentation> cards, int tableauPileId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}

		game.moveCards(toCardStack(cards), tableauPileId);
		
		gameRepository.save(game);
	}

	public void flipCard(String gameId, int tableauPileId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}
		
		game.flipCard(tableauPileId);
		
		gameRepository.save(game);
	}
	
	public void promoteCard(String gameId, PlayingCardRepresentation request) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}
		
		game.promoteCard(request.toPlayingCard());
		
		gameRepository.save(game);
	}
		
	private CardStack toCardStack(List<PlayingCardRepresentation> cards) {
		List<PlayingCard> playingCards = newArrayList();
		
		for (PlayingCardRepresentation card : cards) {
			playingCards.add(card.toPlayingCard());
		}		
		
		return new CardStack(playingCards);
	}
}