package com.patience.klondike.infrastructure.persistence.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.TableauPile;

public class TableauPileDO {

	private int tableauPileId;
	
	private List<PlayingCardDO> unflipped = newArrayList();
		
	private List<PlayingCardDO> flipped = newArrayList();
	
	public TableauPileDO() {	
	}
	
	public TableauPileDO(TableauPile tableauPile) {
		this.tableauPileId = tableauPile.tableauPileId();
		
		for (PlayingCard card : tableauPile.unflippedCards()) {
			this.unflipped.add(new PlayingCardDO(card));
		}
		
		for (PlayingCard card : tableauPile.flippedCards()) {
			this.flipped.add(new PlayingCardDO(card));
		}
	}
	
	public int getTableauPileId() {
		return tableauPileId;
	}
	
	public void setTableauPileId(int tableauPileId) {
		this.tableauPileId = tableauPileId;
	}
	
	public List<PlayingCardDO> getUnflipped() {
		return unflipped;
	}
	
	public void setUnflipped(List<PlayingCardDO> unflipped) {
		this.unflipped = unflipped;
	}
	
	public List<PlayingCardDO> getFlipped() {
		return flipped;
	}
	
	public void setFlipped(List<PlayingCardDO> flipped) {
		this.flipped = flipped;
	}
	
	public TableauPile toTableauPile() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList();
		
		for (PlayingCardDO playingCardDO : unflipped) {
			unflippedCards.add(playingCardDO.toPlayingCard());
		}
		
		for (PlayingCardDO playingCardDO : flipped) {
			flippedCards.add(playingCardDO.toPlayingCard());
		}
		
		return new TableauPile(tableauPileId, unflippedCards, flippedCards);
	}
}