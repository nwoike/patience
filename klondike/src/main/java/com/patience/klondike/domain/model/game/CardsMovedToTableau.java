package com.patience.klondike.domain.model.game;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;
import com.patience.klondike.domain.model.game.score.PileType;

public class CardsMovedToTableau implements DomainEvent {

	private GameId gameId;
	
	private List<PlayingCard> cards;
	
	private PileType origin;
	
	private DateTime occurredOn;

	private int eventVersion = 1;
	
	public CardsMovedToTableau(GameId gameId, List<PlayingCard> cards, PileType origin) {
		this.gameId = gameId;
		this.cards = cards;
		this.origin = origin;
		this.occurredOn = DateTime.now();
	}
	
	@JsonCreator
	public CardsMovedToTableau(@JsonProperty("gameId") GameId gameId, 
			@JsonProperty("cards") List<PlayingCard> cards,
			@JsonProperty("origin") PileType origin, 
			@JsonProperty("occurredOn") DateTime occurredOn) {
		this.gameId = gameId;
		this.cards = cards;
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
	public List<PlayingCard> cards() {
		return cards;
	}
	
	@Override
	@JsonProperty
	public int eventVersion() {		
		return eventVersion;
	}

	@Override
	@JsonProperty
	public String eventType() {		
		return "patience.klondike.CardsMovedToTableau";
	}

	@Override
	@JsonProperty
	public DateTime occurredOn() {		
		return occurredOn;
	}
}