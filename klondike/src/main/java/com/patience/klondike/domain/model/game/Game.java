package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.Entity;
import com.patience.klondike.application.IllegalMoveException;

public class Game extends Entity {

	private GameId gameId;

	private Stock stock;
	
	private Waste waste;
	
	private List<Foundation> foundations = newArrayList();
	
	private List<TableauPile> tableauPiles = newArrayList();
	
	public Game(GameId gameId) {
		this.gameId = checkNotNull(gameId, "GameId must be provided.");	
		new Initializer().setupNewGame();
	}

	public Game(GameId gameId, Stock stock, Waste waste,
			List<Foundation> foundations, List<TableauPile> tableauPiles) {
		this.gameId = checkNotNull(gameId, "GameId must be provided.");
		this.stock = checkNotNull(stock, "Stock must be provided.");
		this.waste = checkNotNull(waste, "Waste must be provided.");
		this.foundations = checkNotNull(foundations, "Foundations must be provided.");
		this.tableauPiles = checkNotNull(tableauPiles, "TableauPiles must be provided.");
	}
	
	/**
	 * Drawing a card against an empty stock will restock the waste pile.
	 */
	public void drawCard() {		
		try {
			PlayingCard card = stock.drawCard();
			waste.addCard(card);
			
		} catch (EmptyStockException e) {
			List<PlayingCard> wasted = waste.playingCards();
			Collections.reverse(wasted);
			stock.restock(wasted);
			waste.clear();
		}		
	}

	public void moveCards(CardStack stack, int destinationTableauPileId) {
		TableauPile destination = tableauPileOf(destinationTableauPileId);		
		PlayingCard topCard = stack.topCard();
		
		if (waste.topCardMatches(topCard)) { 
			waste.removeTopCard();
			destination.addCards(stack);
			return;			
		}
		
		for (Foundation foundation : foundations) {
			if (foundation.topCardMatches(topCard)) {		
				foundation.removeTopCard();
				destination.addCards(stack);
				return;
			}
		}
			
		for (TableauPile origin : tableauPiles) {
			if (origin.contains(stack)) {			
				origin.removeCards(stack);
				destination.addCards(stack);
				return;
			}				
		}
		
		throw new IllegalMoveException("Cards were not able to be moved to destination.");
	}
	
	public void flipCard(int tableauPileId) {
		TableauPile tableauPile = tableauPileOf(tableauPileId);
		tableauPile.flipTopCard();
	}	
	
	public void promoteCard(PlayingCard playingCard, int destinationFoundationId) {
		checkNotNull(playingCard, "Playing card must be provided.");
		
		if (waste.topCardMatches(playingCard)) {	
			waste.removeTopCard();
			addCardToFoundation(playingCard, destinationFoundationId);
			return;			
		}
		
		for (Foundation foundation : foundations) {
			if (foundation.topCardMatches(playingCard)) { 
				foundation.removeTopCard();
				addCardToFoundation(playingCard, destinationFoundationId);
				return;
			}
		}			

		for (TableauPile tableauPile : tableauPiles) {
			if (tableauPile.topCardMatches(playingCard)) { 
				tableauPile.removeCards(new CardStack(playingCard));
				addCardToFoundation(playingCard, destinationFoundationId);
				return;
			}
		}

		throw new IllegalMoveException("Card was not able to be promoted.");
	}

	public boolean isWinner(WinnableChecker winnableChecker) {
		checkNotNull(winnableChecker, "WinnableChecker must be provided.");
		return winnableChecker.isWinnable(this);
	}
	
	private void addCardToFoundation(PlayingCard playingCard, int foundationId) {
		Foundation foundation = foundationOf(foundationId);
		foundation.addCard(playingCard);
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
	
	class Initializer {
		
		private void setupNewGame() {
			Deck deck = new Deck();
			deck.shuffle(new Random().nextLong());
			
			initializeStock(deck);			
			initialFoundations();
			initializeTableau(deck);
			
			waste = new Waste();
			
			assert deck.isEmpty();
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