package com.patience.common.domain.model.cardstack.style;

import com.patience.common.domain.model.card.CardStack;

public class NotCardStackingStyle extends AbstractCardStackingStyle {

	private CardStackingStyle style;

	public NotCardStackingStyle(final CardStackingStyle spec) {
		this.style = spec;
	}

	public boolean isSatisfiedBy(final CardStack stack) {
		return !style.isSatisfiedBy(stack);
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack, StackingStyleTracker tracker) {
		return !style.isSatisfiedBy(stack, tracker);
	}
	
	@Override
	public String toString() {
		return "!(" + style.toString() + ")";
	}
}