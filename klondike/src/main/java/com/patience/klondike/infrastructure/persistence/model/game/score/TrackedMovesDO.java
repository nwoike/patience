package com.patience.klondike.infrastructure.persistence.model.game.score;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newLinkedHashSet;

import java.util.List;
import java.util.Set;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.score.Move;
import com.patience.klondike.domain.model.game.score.TrackedMoves;
import com.patience.klondike.infrastructure.persistence.model.PlayingCardDO;

public class TrackedMovesDO {

	private List<MoveDO> moves = newArrayList();
	
	private List<PlayingCardDO> flippedCards = newArrayList();
	
	private int recycleCount;

	public TrackedMovesDO() {		
	}
	
	public TrackedMovesDO(TrackedMoves trackedMoves) {
		for (Move move : trackedMoves.moves()) {
			this.moves.add(new MoveDO(move));
		}
		
		for (PlayingCard playingCard : trackedMoves.flippedCards()) {
			this.flippedCards.add(new PlayingCardDO(playingCard));
		}
		
		this.recycleCount = trackedMoves.recycleCount();
	}
	
	public List<MoveDO> getMoves() {
		return moves;
	}
	
	public void setMoves(List<MoveDO> moves) {
		this.moves = moves;
	}
	
	public void setFlippedCards(List<PlayingCardDO> flippedCards) {
		this.flippedCards = flippedCards;
	}
	
	public List<PlayingCardDO> getFlippedCards() {
		return flippedCards;
	}
		
	public int getRecycleCount() {
		return recycleCount;
	}
	
	public void setRecycleCount(int recycleCount) {
		this.recycleCount = recycleCount;
	}

	public TrackedMoves toTrackedMoves() {		
		Set<Move> moves = newLinkedHashSet();
		Set<PlayingCard> flippedCards = newLinkedHashSet();
		
		for (MoveDO moveDO : this.moves) {
			moves.add(moveDO.toMove());
		}
		
		for (PlayingCardDO playingCardDO : this.flippedCards) {
			flippedCards.add(playingCardDO.toPlayingCard());
		}
		
		return new TrackedMoves(moves, flippedCards, recycleCount);
	}
}
