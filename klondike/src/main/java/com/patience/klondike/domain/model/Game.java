package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.patience.common.domain.model.CardStack;
import com.patience.common.domain.model.PlayingCard;
import com.patience.common.domain.model.Suit;

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
