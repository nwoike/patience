package com.patience.klondike.solver;

import com.google.common.base.MoreObjects;

public class DrawCard extends Move {

	@Override
	public void play(MoveHandler handler) {
		handler.drawCard();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		return this.getClass() == obj.getClass();
	}

	@Override
	public int hashCode() {
		return 151;
	}
	
	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.toString();
	}
}
