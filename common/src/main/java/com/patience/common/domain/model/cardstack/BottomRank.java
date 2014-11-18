package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class BottomRank extends AbstractCardStackingStyle {

	private Rank rank;
	
	public BottomRank(Rank rank) {
		this.rank = checkNotNull(rank, "Rank must be provided.");
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
	
		if (stack.isEmpty()) {
			return false;
		}
		
		return rank.equals(stack.bottomCard().rank());
	}
}