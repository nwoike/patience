package com.patience.klondike.application.representation;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import com.patience.klondike.domain.model.game.Foundation;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.TableauPile;

public class GameRepresentation {

	private final String gameId;
	
	private final SettingsRepresentation settings;
		
	private final StockRepresentation stock;
	
	private final WasteRepresentation waste;
	
	private final Map<String, FoundationRepresentation> foundations = newHashMap();
	
	private final Map<String, TableauPileRepresentation> tableau = newHashMap();
	
	private final boolean winnable;
	
	public GameRepresentation(Game game, boolean winnable) {
		this.gameId = game.gameId().id();
		this.settings = new SettingsRepresentation(game.settings());		
		this.stock = new StockRepresentation(game.stock());
		this.waste = new WasteRepresentation(game.waste());
		this.winnable = winnable;
		
		for (TableauPile tableauPile : game.tableauPiles()) {
			this.tableau.put(String.valueOf(tableauPile.tableauPileId()), new TableauPileRepresentation(tableauPile));
		} 
		
		for (Foundation foundation : game.foundations()) {
			this.foundations.put(String.valueOf(foundation.foundationId()), new FoundationRepresentation(foundation));
		}	
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public SettingsRepresentation getSettings() {
		return settings;
	}
		
	public StockRepresentation getStock() {
		return stock;
	}
	
	public WasteRepresentation getWaste() {
		return waste;
	}
	
	public Map<String, FoundationRepresentation> getFoundations() {
		return foundations;
	}
	
	public Map<String, TableauPileRepresentation> getTableau() {
		return tableau;
	}
	
	public boolean isWinnable() {
		return winnable;
	}
}