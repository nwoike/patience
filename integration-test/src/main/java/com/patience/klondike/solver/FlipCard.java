package com.patience.klondike.solver;

import com.google.common.base.MoreObjects;

public class FlipCard extends Move {

	private int tableauPileId;

	public FlipCard(int tableauPileId) {
		this.tableauPileId = tableauPileId;	
	}
		
	public int tableauPileId() {
		return tableauPileId;
	}
	
	@Override
	public void play(MoveHandler handler) {
		handler.flipCard(tableauPileId);
	}
	
	@Override
	protected int priority() {
		return 10;
	}	

	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			FlipCard other = (FlipCard) obj;
			return tableauPileId == other.tableauPileId;
		}
			
		return false;
	}
	
	@Override
	public int hashCode() {
		return 653 * new Integer(tableauPileId).hashCode();
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("tableauPileId", tableauPileId)
			.toString();
	}
}
