package com.patience.klondike.infrastructure.persistence.model;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Stock;

public class StockDO {

	private List<PlayingCard> playingCards;

	public StockDO() {
	}
	
	public StockDO(Stock stock) {
		this.playingCards = stock.playingCards();
	}
	
	public List<PlayingCard> getPlayingCards() {
		return playingCards;
	}
	
	public void setPlayingCards(List<PlayingCard> playingCards) {
		this.playingCards = playingCards;
	}
	
	public Stock toStock() {
		return new Stock(playingCards);
	}
}
