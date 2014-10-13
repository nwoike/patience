package com.patience.klondike.application.representation;

import com.patience.klondike.domain.model.Game;

public class GameRepresentation {

	private String gameId;
	
	private StockRepresentation stock;
	
	private WasteRepresentation waste;
	
	private FoundationsRepresentation foundations;
	
	private TableauRepresentation tableau;
	
	public GameRepresentation(Game game) {
		this.gameId = game.gameId().id();
		this.stock = new StockRepresentation(game.stock());
		this.waste = new WasteRepresentation(game.waste());
		this.foundations = new FoundationsRepresentation(game.foundations());
		this.tableau = new TableauRepresentation(game.tableauPiles());
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public StockRepresentation getStock() {
		return stock;
	}
	
	public WasteRepresentation getWaste() {
		return waste;
	}
	
	public FoundationsRepresentation getFoundations() {
		return foundations;
	}
	
	public TableauRepresentation getTableau() {
		return tableau;
	}
}