package com.patience.klondike.domain.model.game;

import org.joda.time.DateTime;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEvent;
import com.patience.klondike.domain.model.game.score.PileType;

public class CardPromoted implements DomainEvent {

	private GameId gameId;
	
	private PlayingCard card;
	
	private PileType origin;
	
	private DateTime occurredOn;

	private String eventType = "patience.klondike.CardPromoted";
	
	private int eventVersion = 1;
	
	public CardPromoted(GameId gameId, PlayingCard card, PileType origin) {
		this.gameId = gameId;
		this.card = card;
		this.origin = origin;
		this.occurredOn = DateTime.now();
	}
	
	public GameId gameId() {
		return gameId;
	}
	
	public PileType origin() {
		return origin;
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