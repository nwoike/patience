package com.patience.klondike.infrastructure.persistence.model.game;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.klondike.domain.model.game.Foundation;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.Settings;
import com.patience.klondike.domain.model.game.TableauPile;

public class GameDO {

	private String gameId;
	
	private SettingsDO settingsDO;
	
	private StockDO stockDO;
	
	private WasteDO wasteDO;
	
	private List<FoundationDO> foundationDOs = newArrayList();
	
	private List<TableauPileDO> tableauPileDOs = newArrayList();
	
	public GameDO() {
	}
	
	public GameDO(Game game) {
		Settings settings = game.settings();
				
		this.gameId = game.gameId().id();
		this.settingsDO = new SettingsDO(settings);
		this.stockDO = new StockDO(game.stock(), settings.passCount());
		this.wasteDO = new WasteDO(game.waste());
		
		for (TableauPile tableauPile : game.tableauPiles()) {
			this.tableauPileDOs.add(new TableauPileDO(tableauPile));
		} 
		
		for (Foundation foundation : game.foundations()) {
			this.foundationDOs.add(new FoundationDO(foundation));
		}	
	}
	
	public String getGameId() {
		return gameId;
	}
	
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	
	public void setSettingsDO(SettingsDO settingsDO) {
		this.settingsDO = settingsDO;
	}
	
	public SettingsDO getSettingsDO() {
		return settingsDO;
	}
		
	public StockDO getStock() {
		return stockDO;
	}
	
	public void setStock(StockDO stock) {
		this.stockDO = stock;
	}
	
	public WasteDO getWaste() {
		return wasteDO;
	}
	
	public void setWaste(WasteDO waste) {
		this.wasteDO = waste;
	}
		
	public List<FoundationDO> getFoundations() {
		return foundationDOs;
	}
	
	public void setFoundations(List<FoundationDO> foundations) {
		this.foundationDOs = foundations;
	}
	
	public List<TableauPileDO> getTableau() {
		return tableauPileDOs;
	}
	
	public void setTableau(List<TableauPileDO> tableau) {
		this.tableauPileDOs = tableau;
	}
	
	public Game toGame() {
		List<Foundation> foundations = newArrayList();
		List<TableauPile> tableauPiles = newArrayList();
		
		for (FoundationDO foundationDO : foundationDOs) {
			foundations.add(foundationDO.toFoundation());
		}
		
		for (TableauPileDO tableauPileDO : tableauPileDOs) {
			tableauPiles.add(tableauPileDO.toTableauPile());
		}
		
		return new Game(new GameId(gameId),
			settingsDO.toSettings(),
			stockDO.toStock(),
			wasteDO.toWaste(),
			foundations,
			tableauPiles);
	}
}