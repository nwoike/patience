package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.patience.domain.model.event.DomainEvent;

public class GameWon implements DomainEvent {

	private final GameId gameId;

	private final DateTime occurredOn;

	private String eventType = "patience.klondike.GameWon";
	
	private final int eventVersion;
	
	public GameWon(GameId gameId) {
		this.gameId = gameId;
		this.occurredOn = DateTime.now();
		this.eventVersion = 1;
	}
		
	public GameId gameId() {
		return gameId;
	}
	
	public DateTime occurredOn() {
		return occurredOn;
	}
	
	@Override
	public int eventVersion() {
		return eventVersion;
	}
	
	public String eventType() {
		return eventType;
	}
}
