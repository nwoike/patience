package com.patience.klondike.infrastructure.persistence.model.game;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.Stock;
import com.patience.klondike.infrastructure.persistence.model.PlayingCardDO;

public class StockDO {

	private List<PlayingCardDO> cards = newArrayList();

	public StockDO() {
	}
	
	public StockDO(Stock stock) {
		for (PlayingCard playingCard : stock.playingCards()) {
			this.cards.add(new PlayingCardDO(playingCard));
		}		
	}
	
	public List<PlayingCardDO> getPlayingCards() {
		return cards;
	}
	
	public void setPlayingCards(List<PlayingCardDO> playingCards) {
		this.cards = playingCards;
	}
	
	public Stock toStock() {
		List<PlayingCard> playingCards = newArrayList();
		
		for (PlayingCardDO playingCardDO : cards) {
			playingCards.add(playingCardDO.toPlayingCard());
		}
		
		return new Stock(playingCards);
	}
}
