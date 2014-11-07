package com.patience.klondike.infrastructure.persistence.model.game;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.PassCount;
import com.patience.klondike.domain.model.game.Stock;
import com.patience.klondike.infrastructure.persistence.model.PlayingCardDO;

public class StockDO {

	private List<PlayingCardDO> cards = newArrayList();

	private String passCount;
	
	private int passes;
	
	public StockDO() {
	}
	
	public StockDO(Stock stock, PassCount passCount) {
		this.passCount = passCount.name();
		this.passes = stock.passes();
		
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
	
	public int getPasses() {
		return passes;
	}
	
	public void setPasses(int passes) {
		this.passes = passes;
	}
	
	public String getPassCount() {
		return passCount;
	}
	
	public void setPassCount(String passCount) {
		this.passCount = passCount;
	}
	
	public Stock toStock() {
		List<PlayingCard> playingCards = newArrayList();
		
		for (PlayingCardDO playingCardDO : cards) {
			playingCards.add(playingCardDO.toPlayingCard());
		}
		
		return new Stock(playingCards, PassCount.valueOf(passCount), passes);
	}
}
