package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.domain.model.event.DomainEvent;

public class GameCreated implements DomainEvent {

	private final GameId gameId;

	private final DateTime occurredOn;
	
	private int eventVersion;
	
	public GameCreated(GameId gameId) {
		this.gameId = gameId;
		this.occurredOn = DateTime.now();
		this.eventVersion = 1;
	}
	
	@JsonProperty
	public GameId gameId() {
		return gameId;
	}
	
	@JsonProperty
	public DateTime occurredOn() {
		return occurredOn;
	}
	
	@Override
	@JsonProperty
	public int eventVersion() {
		return eventVersion;
	}
	
	@JsonProperty
	public String eventType() {
		return "patience.klondike.GameCreated";
	}
}
