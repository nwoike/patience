package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Random;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.domain.model.Entity;
import com.patience.domain.model.event.DomainEventPublisher;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.domain.model.game.scorer.PileType;
import com.patience.klondike.domain.service.game.WinChecker;

public class Game extends Entity {

	private GameId gameId;

	private Stock stock;
	
	private Waste waste;
	
	private List<Foundation> foundations = newArrayList();
	
	private List<TableauPile> tableauPiles = newArrayList();
	
	public Game(GameId gameId) {
		this.setGameId(gameId);
		
		new GameInitializer().setupNewGame();
		
		DomainEventPublisher
			.instance()
			.publish(new GameCreated(gameId));
	}

	public Game(GameId gameId, Stock stock, Waste waste,
			List<Foundation> foundations, List<TableauPile> tableauPiles) {
		this.setGameId(gameId);
		this.stock = checkNotNull(stock, "Stock must be provided.");
		this.waste = checkNotNull(waste, "Waste must be provided.");
		this.foundations = checkNotNull(foundations, "Foundations must be provided.");
		this.tableauPiles = checkNotNull(tableauPiles, "TableauPiles must be provided.");
	}
	
	public void drawCard() {			
		PlayingCard card = stock.drawCard();
		
		if (card != null) {
			waste.addCard(card);
			
		} else {
			if (waste.isEmpty()) {
				return;
			}
			
			stock.recycle(waste);
			
			DomainEventPublisher
				.instance()
				.publish(new StockRecycled(gameId));
		}
	}

	public void moveCards(CardStack stack, int destinationTableauPileId) {
		TableauPile destination = tableauPileOf(destinationTableauPileId);		
		PlayingCard card = stack.topCard();
		
		if (waste.topCardMatches(card)) { 
			waste.removeTopCard();
			destination.addCards(stack);
			
			DomainEventPublisher
				.instance()
				.publish(new CardsMovedToTableau(gameId, stack.cards(), PileType.Waste));
			
			return;			
		}
		
		for (Foundation foundation : foundations) {
			if (foundation.topCardMatches(card)) {		
				foundation.removeTopCard();
				destination.addCards(stack);
				
				DomainEventPublisher
					.instance()
					.publish(new CardsMovedToTableau(gameId, stack.cards(), PileType.Foundation));
				
				return;
			}
		}
			
		for (TableauPile origin : tableauPiles) {
			if (origin.contains(stack)) {			
				origin.removeCards(stack);
				destination.addCards(stack);
				
				DomainEventPublisher
					.instance()
					.publish(new CardsMovedToTableau(gameId, stack.cards(), PileType.Tableau));
				
				return;
			}				
		}
		
		throw new IllegalMoveException("Cards were not able to be moved to destination.");
	}
	
	public void flipCard(int tableauPileId) {
		TableauPile tableauPile = tableauPileOf(tableauPileId);		
		tableauPile.flipTopCard();
		
		DomainEventPublisher
			.instance()
			.publish(new CardFlipped(gameId, tableauPile.flippedCards().topCard()));
	}	
	
	public void promoteCard(PlayingCard card, int destinationFoundationId) {
		checkNotNull(card, "Playing card must be provided.");
		
		Foundation destination = foundationOf(destinationFoundationId);
		
		if (waste.topCardMatches(card)) {	
			waste.removeTopCard();
			destination.addCard(card);
			
			DomainEventPublisher
				.instance()
				.publish(new CardPromoted(gameId, card, PileType.Waste));
			
			if (isWon()) {
				DomainEventPublisher
					.instance()
					.publish(new GameWon(gameId));
			}
			
			return;			
		}
		
		for (Foundation candidate : foundations) {						
			if (candidate.topCardMatches(card) && !candidate.equals(destination)) { 
				candidate.removeTopCard();
				destination.addCard(card);							
				return;
			}
		}			

		for (TableauPile tableauPile : tableauPiles) {
			if (tableauPile.topCardMatches(card)) { 
				tableauPile.removeCard(card);
				destination.addCard(card);
				
				DomainEventPublisher
					.instance()
					.publish(new CardPromoted(gameId, card, PileType.Tableau));
				
				if (isWon()) {
					DomainEventPublisher
						.instance()
						.publish(new GameWon(gameId));
				}
				
				return;
			}
		}
		
		throw new IllegalMoveException("Card was not able to be promoted.");
	}

	public boolean isWon() {
		for (Foundation foundation : foundations) {
			if (foundation.topCard().rank() != Rank.King) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isWinnable(WinChecker winnableChecker) {
		checkNotNull(winnableChecker, "WinnableChecker must be provided.");
		return winnableChecker.isWinnable(this);
	}
	
	private Foundation foundationOf(int foundationId) {
		try {
			return foundations.get(foundationId - 1);
			
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalMoveException("Invalid foundationId");
		}
	}
	
	private TableauPile tableauPileOf(int tableauPileId) {		
		try {
			return tableauPiles.get(tableauPileId - 1);
			
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalMoveException("Invalid tableauPileId");
		}
	}

	public GameId gameId() {
		return gameId;
	}
	
	public Stock stock() {
		return stock;
	}
	
	public Waste waste() {
		return waste;
	}
	
	public List<Foundation> foundations() {
		return newArrayList(foundations);
	}
	
	public List<TableauPile> tableauPiles() {
		return newArrayList(tableauPiles);
	}
	
	private void setGameId(GameId gameId) {
		this.gameId = checkNotNull(gameId, "GameId must be provided.");	
		this.gameId = gameId;
	}
	
	private class GameInitializer {
		
		private void setupNewGame() {
			Deck deck = new Deck();
			deck.shuffle(new Random().nextLong());
			
			initializeWaste();
			initializeStock(deck);			
			initialFoundations();
			initializeTableau(deck);			
		}

		private void initializeWaste() {
			waste = new Waste();			
		}

		private void initializeStock(Deck deck) {
			List<PlayingCard> cards = newArrayList();
			
			for (int i = 0; i < 24; i++) {
				cards.add(deck.drawCard());
			}
			
			stock = new Stock(cards);		
		}

		private void initializeTableau(Deck deck) {
			for (int index = 1; index <= 7; index++) {		
				List<PlayingCard> unflippedCards = newArrayList(); 
				
				for (int i = 1; i < index; i++) {
					unflippedCards.add(deck.drawCard());
				}
				
				TableauPile tableauPile = new TableauPile(index, unflippedCards, newArrayList(deck.drawCard()));
				tableauPiles.add(tableauPile);			
			}
		}

		private void initialFoundations() {
			for (int i = 1; i <= 4; i++) {
				foundations.add(new Foundation(i));
			}
		}
	}
}