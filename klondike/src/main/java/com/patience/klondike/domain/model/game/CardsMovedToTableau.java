package com.patience.klondike.domain.model.game;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;
import com.patience.klondike.domain.model.game.score.PileType;

public class CardsMovedToTableau implements DomainEvent {

	private GameId gameId;
	
	private List<PlayingCard> cards;
	
	private PileType origin;
	
	private DateTime occurredOn;

	private String eventType = "patience.klondike.CardsMovedToTableau";
	
	private int eventVersion = 1;
	
	public CardsMovedToTableau(GameId gameId, List<PlayingCard> cards, PileType origin) {
		this.gameId = gameId;
		this.cards = cards;
		this.origin = origin;
		this.occurredOn = DateTime.now();
	}
		
	public GameId gameId() {
		return gameId;
	}
	
	public PileType origin() {
		return origin;
	}
	
	public List<PlayingCard> cards() {
		return cards;
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
	@JsonProperty
	public DateTime occurredOn() {		
		return occurredOn;
	}
}