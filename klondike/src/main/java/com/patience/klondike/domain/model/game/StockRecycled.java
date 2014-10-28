package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.domain.model.event.DomainEvent;

public class StockRecycled implements DomainEvent {

	private GameId gameId;
	
	private DateTime occurredOn;

	private int eventVersion = 1;
	
	public StockRecycled(GameId gameId) {
		this.gameId = gameId;
		this.occurredOn = DateTime.now();
	}
	
	@JsonCreator
	public StockRecycled(@JsonProperty("gameId") GameId gameId, 
			@JsonProperty("occurredOn") DateTime occurredOn) {
		this.gameId = gameId;
		this.occurredOn = occurredOn;
	}
	
	@JsonProperty
	public GameId gameId() {
		return gameId;
	}
	
	@Override
	@JsonProperty
	public String eventType() {		
		return "patience.klondike.StockRecycled";
	}

	@Override
	@JsonProperty
	public int eventVersion() {		
		return eventVersion;
	}

	@Override
	@JsonProperty
	public DateTime occurredOn() {		
		return occurredOn;
	}
}