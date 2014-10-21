package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;

public class CardFlipped implements DomainEvent {

	private GameId gameId;
	
	private PlayingCard card;
	
	private DateTime occurredOn;

	private int eventVersion = 1;
	
	public CardFlipped(GameId gameId, PlayingCard card) {
		this.gameId = gameId;
		this.card = card;
		this.occurredOn = DateTime.now();
	}
	
	@JsonCreator
	public CardFlipped(@JsonProperty("gameId") GameId gameId, @JsonProperty("card") PlayingCard card,
			@JsonProperty("occurredOn") DateTime occurredOn) {
		this.gameId = gameId;
		this.card = card;
		this.occurredOn = occurredOn;
	}
	
	@JsonProperty
	public GameId gameId() {
		return gameId;
	}
	
	@JsonProperty
	public PlayingCard card() {
		return card;
	}
	
	@Override
	@JsonProperty
	public int eventVersion() {		
		return eventVersion;
	}

	@Override
	@JsonProperty
	public String eventType() {		
		return "patience.klondike.CardFlipped";
	}

	@Override
	@JsonProperty
	public DateTime occurredOn() {		
		return occurredOn;
	}
}