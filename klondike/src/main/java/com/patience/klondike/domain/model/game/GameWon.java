package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.domain.model.event.DomainEvent;

public class GameWon implements DomainEvent {

	private final GameId gameId;

	private final DateTime occurredOn;

	private final int eventVersion;
	
	public GameWon(GameId gameId) {
		this.gameId = gameId;
		this.occurredOn = DateTime.now();
		this.eventVersion = 1;
	}
	
	@JsonCreator
	public GameWon(@JsonProperty("gameId") GameId gameId, @JsonProperty("occurredOn") DateTime occurredOn,
			@JsonProperty("eventVersion") int eventVersion) {
		this.gameId = gameId;
		this.occurredOn = occurredOn;
		this.eventVersion = eventVersion;		
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
		return "patience.klondike.GameWon";
	}
}
