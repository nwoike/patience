package com.patience.klondike.solver;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.MoreObjects;
import com.patience.common.domain.model.card.PlayingCard;

public class MoveCard extends Move {

	private PlayingCard card;
	
	private int tableauPileId;

	private PileType origin;

	public MoveCard(PileType origin, PlayingCard card, int tableauPileId) {
		this.origin = checkNotNull(origin, "Origin PileType must be provided.");
		this.card = checkNotNull(card, "PlayingCard must be provided.");
		this.tableauPileId = tableauPileId;	
	}
	
	public PileType origin() {
		return origin;
	}
	
	public PlayingCard card() {
		return card;
	}
	
	public int tableauPileId() {
		return tableauPileId;
	}
	
	protected int priority() {		
		return PileType.Waste == origin ? 3 : 4;
	}
	
	@Override
	public void play(MoveHandler handler) {
		handler.moveCards(card, tableauPileId);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			MoveCard other = (MoveCard) obj;
			
			return origin.equals(other.origin())
				&& card.equals(other.card())
				&& tableauPileId == other.tableauPileId();
		}
		return false;
	}
	
	
	@Override
	public int hashCode() {
		return 277 * origin.hashCode() + card.hashCode() + new Integer(tableauPileId).hashCode();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("origin", origin)
			.add("card", card)
			.add("tableauPileId", tableauPileId)
			.toString();
	}
}
