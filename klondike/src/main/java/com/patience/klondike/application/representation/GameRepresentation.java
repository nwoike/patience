package com.patience.klondike.application.representation;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Collection;
import java.util.Map;

import com.patience.klondike.domain.model.Foundation;
import com.patience.klondike.domain.model.Game;
import com.patience.klondike.domain.model.TableauPile;

public class GameRepresentation {

	private String gameId;
	
	private StockRepresentation stock;
	
	private WasteRepresentation waste;
	
	private Collection<FoundationRepresentation> foundations = newArrayList();
	
	private Map<String, TableauPileRepresentation> tableau = newHashMap();
	
	public GameRepresentation(Game game) {
		this.gameId = game.gameId().id();
		this.stock = new StockRepresentation(game.stock());
		this.waste = new WasteRepresentation(game.waste());
		
		for (TableauPile tableauPile : game.tableauPiles()) {
			this.tableau.put(String.valueOf(tableauPile.tableauPileId()), new TableauPileRepresentation(tableauPile));
		} 
		
		for (Foundation foundation : game.foundations()) {
			this.foundations.add(new FoundationRepresentation(foundation));
		}	
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
	
	public Collection<FoundationRepresentation> getFoundations() {
		return foundations;
	}
	
	public Map<String, TableauPileRepresentation> getTableau() {
		return tableau;
	}
}