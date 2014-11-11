package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.patience.domain.model.event.DomainEvent;

public class StockRecycled implements DomainEvent {

	private GameId gameId;
	
	private DateTime occurredOn;

	private String eventType = "patience.klondike.StockRecycled";
	
	private int eventVersion = 1;
	
	public StockRecycled(GameId gameId) {
		this.gameId = gameId;
		this.occurredOn = DateTime.now();
	}
		
	public GameId gameId() {
		return gameId;
	}
	
	@Override
	public DateTime occurredOn() {		
		return occurredOn;
	}
	
	@Override
	public String eventType() {		
		return eventType;
	}

	@Override
	public int eventVersion() {		
		return eventVersion;
	}
}