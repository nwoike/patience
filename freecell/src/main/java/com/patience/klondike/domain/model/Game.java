package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Suit;

public class Game {

	private GameId gameId;

	private Stock stock;
	
	private Waste waste;
	
	private Map<Suit, Foundation> foundations;
	
	private List<TableauPile> tableauPiles;
	
	public Game(GameId gameId) {
		this.gameId = checkNotNull(gameId, "GameId must be provided.");	
		initializeGame(gameId);
	}

	private void initializeGame(GameId gameId) {
		Deck deck = new Deck();
		deck.shuffle(Integer.parseInt(gameId.id(), 36));
		
		initializeStock(deck);			
		initialFoundations();
		initializeTableau(deck);
		
		this.waste = new Waste();
		assert deck.isEmpty();
	}

	private void initializeStock(Deck deck) {
		List<PlayingCard> cards = newArrayList();
		
		for (int i = 0; i < 24; i++) {
			cards.add(deck.drawCard());
		}
		
		this.stock = new Stock(cards);		
	}

	private void initializeTableau(Deck deck) {
		this.tableauPiles = newArrayList();
		
		for (int index = 1; index <= 7; index++) {		
			List<PlayingCard> unflippedCards = newArrayList(); 
			
			for (int i = 1; i < index; i++) {
				unflippedCards.add(deck.drawCard());
			}
			
			TableauPile tableauPile = new TableauPile(index, unflippedCards, new CardStack(deck.drawCard()));
			this.tableauPiles.add(tableauPile);			
		}
	}

	private void initialFoundations() {
		this.foundations = newHashMap();
		
		for (Suit suit : Suit.values()) {
			Foundation foundation = new Foundation(suit);
			this.foundations.put(suit, foundation);			
		}
	}
	
	public void drawCard() {		
		try {
			PlayingCard card = stock.drawCard();
			waste.addCard(card);
			
		} catch (StockEmptyException e) {
			this.stock.restock(waste.playingCards());
			waste.clear();
		}		
	}

	public void moveCards(CardStack stack, int destinationTableauPileId) {
		TableauPile destination = locateTableauPile(destinationTableauPileId);
		
		if (stack.cardCount() == 1 && waste.topCard() == stack.topCard()) {
			waste.removeTopCard();
			destination.addCards(stack);
			return;
			
		} else {
			for (TableauPile origin : tableauPiles) {
				if (origin.flippedCards().contains(stack)) {				
					origin.removeCards(stack);
					destination.addCards(stack);
					return;
				}				
			}
		}
		
		throw new IllegalArgumentException("Cards were not able to be moved to destination.");
	}
	
	public void flipCard(int tableauPileId) {
		TableauPile tableauPile = locateTableauPile(tableauPileId);
		tableauPile.flipTopCard();
	}	
	
	public void promoteCard(PlayingCard playingCard) {
		boolean found = false;
		
		if (waste.topCard() == playingCard) {
			waste.removeTopCard();
			found = true;
			
		} else {
			for (TableauPile tableauPile : tableauPiles) {
				if (tableauPile.flippedCards().topCard() == playingCard) {
					tableauPile.removeCards(new CardStack(playingCard));
					found = true;
					break;
				}
			}
		}
		
		if (!found) {
			throw new IllegalArgumentException("Card was not able to be promoted.");
		}
		
		foundations.get(playingCard.suit()).addCard(playingCard);
	}
	
	private TableauPile locateTableauPile(int tableauPileId) {
		TableauPile tableauPile = tableauPiles.get(tableauPileId - 1);
				
		if (tableauPile == null) {
			throw new IllegalArgumentException("Invalid tableau pile destination.");
		}
		
		return tableauPile;
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
	
	public Collection<Foundation> foundations() {
		return newArrayList(foundations.values());
	}
	
	public List<TableauPile> tableauPiles() {
		return newArrayList(tableauPiles);
	}
}
