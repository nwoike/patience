package com.patience.klondike.infrastructure.persistence.model.game.score;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.score.Move;
import com.patience.klondike.domain.model.game.score.PileType;

public class MoveDO {

	private String origin;
	
	private String destination;

	private String card;

	public MoveDO() {		
	}
	
	public MoveDO(Move move) {
		this.origin = move.origin().name();
		this.destination = move.destination().name();
		this.card = move.playingCard().name();		
	}
	
	public String getCard() {
		return card;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public void setCard(String card) {
		this.card = card;
	}
	
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Move toMove() {		
		return new Move(PlayingCard.valueOf(card), PileType.valueOf(origin), PileType.valueOf(destination));
	}
}
