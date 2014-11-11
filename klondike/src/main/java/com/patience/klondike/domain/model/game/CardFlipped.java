package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;

public class CardFlipped implements DomainEvent {

	private GameId gameId;
	
	private PlayingCard card;
	
	private DateTime occurredOn;

	private String eventType = "patience.klondike.CardFlipped";
	
	private int eventVersion = 1;
	
	public CardFlipped(GameId gameId, PlayingCard card) {
		this.gameId = gameId;
		this.card = card;
		this.occurredOn = DateTime.now();
	}
	
	public GameId gameId() {
		return gameId;
	}
	
	public PlayingCard card() {
		return card;
	}
	
	@Override
	public int eventVersion() {		
		return eventVersion;
	}

	@Override
	public String eventType() {		
		return eventType;
	}

	@Override
	public DateTime occurredOn() {		
		return occurredOn;
	}
}