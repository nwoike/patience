package com.patience.common.specification;

import com.patience.common.domain.model.card.CardStack;

public class AndCardStackingStyle extends AbstractCardStackingStyle {

	private CardStackingStyle style;
	
	private CardStackingStyle otherStyle;

	public AndCardStackingStyle(final CardStackingStyle style, final CardStackingStyle otherStyle) {
		this.style = style;
		this.otherStyle = otherStyle;
	}

	public boolean isSatisfiedBy(final CardStack stack) {
		return style.isSatisfiedBy(stack) && otherStyle.isSatisfiedBy(stack);
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack, StackingStyleTracker tracker) {
		return style.isSatisfiedBy(stack, tracker) && otherStyle.isSatisfiedBy(stack, tracker);
	}
	
	@Override
	public String toString() {
		return "(" + style.toString() + " && " + otherStyle.toString() + ")";
	}
}
