package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.patience.domain.model.event.DomainEvent;

public class GameCreated implements DomainEvent {

	private final GameId gameId;

	private final Settings settings;
	
	private final DateTime occurredOn;
	
	private String eventType = "patience.klondike.GameCreated";
	
	private int eventVersion;
	
	public GameCreated(GameId gameId, Settings settings) {
		this.gameId = gameId;
		this.settings = settings;
		this.occurredOn = DateTime.now();
		this.eventVersion = 1;
	}
	
	public GameId gameId() {
		return gameId;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public DateTime occurredOn() {
		return occurredOn;
	}
	
	@Override
	public int eventVersion() {
		return eventVersion;
	}
	
	@Override
	public String eventType() {
		return eventType;
	}
}
