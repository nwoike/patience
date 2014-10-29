package com.patience.klondike.application;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.application.representation.GameRepresentation;
import com.patience.klondike.application.representation.PlayingCardRepresentation;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.GameRepository;
import com.patience.klondike.domain.service.game.WinChecker;

@Transactional
public class GameApplicationService {

	private GameRepository gameRepository;

	private WinChecker winChecker;
	
	public GameApplicationService(GameRepository gameRepository, WinChecker winnableChecker) {
		this.gameRepository = checkNotNull(gameRepository, "Game Repository must be provided.");
		this.winChecker = checkNotNull(winnableChecker, "Winnable checker must be provided.");		
	}
	
	public String startGame() {
		GameId gameId = gameRepository.nextIdentity();
		
		Game game = new Game(gameId);
		gameRepository.save(game);
		
		return gameId.toString();
	}
	
	@Transactional(readOnly=true)		
	public GameRepresentation loadGame(String gameId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			return null;
		}
		
		final boolean won = winChecker.isWinnable(game);
		
		return new GameRepresentation(game, won);
	}
	
	public void drawCard(String gameId) {
		Game game = gameOf(gameId);		
		game.drawCard();
		
		gameRepository.save(game);
	}
	
	public void moveCards(String gameId, List<PlayingCardRepresentation> cards, int tableauPileId) {
		Game game = gameOf(gameId);
		game.moveCards(toPlayingCardList(cards), tableauPileId);
		
		gameRepository.save(game);
	}

	public void flipCard(String gameId, int tableauPileId) {
		Game game = gameOf(gameId);		
		game.flipCard(tableauPileId);
		
		gameRepository.save(game);
	}
	
	public void promoteCard(String gameId, PlayingCardRepresentation card, int foundationId) {
		Game game = gameOf(gameId);		
		game.promoteCard(toPlayingCard(card), foundationId);
		
		gameRepository.save(game);
	}

	private Game gameOf(String gameId) {
		Game game = gameRepository.gameOfId(new GameId(gameId));
		
		if (game == null) {
			throw new IllegalArgumentException("Game could not be found.");
		}
		
		return game;
	}
	
	private PlayingCard toPlayingCard(PlayingCardRepresentation representation) {
		return representation.toPlayingCard();
	}
	
	private List<PlayingCard> toPlayingCardList(List<PlayingCardRepresentation> representations) {
		List<PlayingCard> cards = newArrayList();
		
		for (PlayingCardRepresentation representation : representations) {
			cards.add(representation.toPlayingCard());
		}		
		
		return cards;
	}
}