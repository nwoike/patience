package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.domain.model.Entity;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.domain.service.game.WinChecker;

public class Game extends Entity {

	private GameId gameId;

	private Stock stock;
	
	private Waste waste;
	
	private List<Foundation> foundations = newArrayList();
	
	private List<TableauPile> tableauPiles = newArrayList();
	
	public Game(GameId gameId) {
		this.setGameId(gameId);
		
		new GameInitializer().setupNewGame(gameId);
		
		publish(new GameCreated(gameId));
	}

	public Game(GameId gameId, Stock stock, Waste waste,
			List<Foundation> foundations, List<TableauPile> tableauPiles) {
		this.setGameId(gameId);
		this.setStock(stock);
		this.setWaste(waste);
		this.setFoundations(foundations);
		this.setTableauPiles(tableauPiles);
	}
	
	public void drawCard() {	
		PlayingCard card = stock.drawCard();
		
		if (card != null) {
			waste.addCard(card);
			return;
		} 

		// Do not try to recycle an empty waste
		if (waste.isEmpty()) {
			return;
		}
			
		stock.recycle(waste);
		
		publish(new StockRecycled(gameId));
	}

	public void moveCards(List<PlayingCard> cards, int destinationTableauPileId) {
		Pile origin = locateMovableCards(cards);
		TableauPile destination = tableauPileOf(destinationTableauPileId);	
				
		if (origin == null) {
			throw new IllegalMoveException("Cards were not able to be moved to destination.");
		}
		
		origin.removeCards(cards);
		destination.addCards(cards);
		
		publish(new CardsMovedToTableau(gameId, cards, origin.pileType()));
	}
	
	public void flipCard(int tableauPileId) {
		TableauPile tableauPile = tableauPileOf(tableauPileId);		
		tableauPile.flipTopCard();
		
		publish(new CardFlipped(gameId, tableauPile.flippedCards().topCard()));
	}	
	
	public void promoteCard(PlayingCard card, int destinationFoundationId) {
		checkNotNull(card, "Playing card must be provided.");
		
		Pile origin = locateMovableCard(card);
		Foundation destination = foundationOf(destinationFoundationId);		
		
		if (origin == null || origin.equals(destination)) {
			throw new IllegalMoveException("Card was not able to be promoted.");
		}
		
		origin.removeTopCard();
		destination.promoteCard(card);
		
		publish(new CardPromoted(gameId, card, origin.pileType()));
		
		if (isWon()) {
			publish(new GameWon(gameId));
		}	
	}
	
	private Pile locateMovableCard(PlayingCard card) {
		return locateMovableCards(newArrayList(card));
	}
	
	private Pile locateMovableCards(List<PlayingCard> cards) {
		if (waste.topCardsMatch(cards)) {	
			return waste;
		}
		
		for (Foundation foundation : foundations) {						
			if (foundation.topCardsMatch(cards)) { 
				return foundation;
			}
		}
		
		for (TableauPile tableauPile : tableauPiles) {
			if (tableauPile.topCardsMatch(cards)) { 
				return tableauPile;
			}
		}
		
		return null;
	}
	
	public boolean isWon() {
		for (Foundation foundation : foundations) {			
			if (foundation.isEmpty() || foundation.topCard().rank() != Rank.King) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isWinnable(WinChecker winChecker) {
		checkNotNull(winChecker, "WinChecker must be provided.");
		return winChecker.isWinnable(this);
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
	
	private void setStock(Stock stock) {
		this.stock = checkNotNull(stock, "Stock must be provided.");
	}
	
	private void setWaste(Waste waste) {
		this.waste = checkNotNull(waste, "Waste must be provided.");
	}
	
	private void setFoundations(List<Foundation> foundations) {
		this.foundations = checkNotNull(foundations, "Foundations must be provided.");
	}
	
	private void setTableauPiles(List<TableauPile> tableauPiles) {
		this.tableauPiles = checkNotNull(tableauPiles, "TableauPiles must be provided.");
	}	
	
	private class GameInitializer {
		
		private void setupNewGame(GameId gameId) {
			Deck deck = new Deck();
			deck.shuffle(gameId.toSeed());
			
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