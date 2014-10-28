package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;
import com.patience.klondike.domain.model.game.score.PileType;

public class CardPromoted implements DomainEvent {

	private GameId gameId;
	
	private PlayingCard card;
	
	private PileType origin;
	
	private DateTime occurredOn;

	private int eventVersion = 1;
	
	public CardPromoted(GameId gameId, PlayingCard card, PileType origin) {
		this.gameId = gameId;
		this.card = card;
		this.origin = origin;
		this.occurredOn = DateTime.now();
	}
	
	@JsonCreator
	public CardPromoted(@JsonProperty("gameId") GameId gameId, 
			@JsonProperty("card") PlayingCard card,
			@JsonProperty("origin") PileType origin, 
			@JsonProperty("occurredOn") DateTime occurredOn) {
		this.gameId = gameId;
		this.card = card;
		this.origin = origin;
		this.occurredOn = occurredOn;
	}
	
	@JsonProperty
	public GameId gameId() {
		return gameId;
	}
	
	@JsonProperty
	public PileType origin() {
		return origin;
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
		return "patience.klondike.CardPromoted";
	}

	@Override
	@JsonProperty
	public DateTime occurredOn() {		
		return occurredOn;
	}
}