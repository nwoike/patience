package com.patience.klondike.domain.model;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.patience.common.domain.model.Suit;

public class Game {

	private GameId gameId;

	private Deck deck;
	
	private Stock stock;
	
	private Waste waste;
	
	private Map<Suit, Foundation> foundations;
	
	private List<TableauPile> tableauPiles;
	
	public Game(GameId gameId) {
		this.gameId = Preconditions.checkNotNull(gameId, "GameId must be provided.");	
		this.deck = new Deck();
		this.stock = new Stock();
		this.waste = new Waste();
		this.foundations = newHashMap();
		this.tableauPiles = newArrayList();
		
		for (Suit suit : Suit.values()) {
			Foundation foundation = new Foundation(suit);
			this.foundations.put(suit, foundation);			
		}
		
		for (int i = 1; i <= 7; i++) {
			TableauPile tableauPile = new TableauPile(i);
			this.tableauPiles.add(tableauPile);			
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
	
	public Collection<Foundation> foundations() {
		return newArrayList(foundations.values());
	}
	
	public List<TableauPile> tableauPiles() {
		return newArrayList(tableauPiles);
	}
}
