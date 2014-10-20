package com.patience.common.specification;

import com.patience.common.domain.model.card.CardStack;

public class OrCardStackingStyle extends AbstractCardStackingStyle {

	private CardStackingStyle style;
	
	private CardStackingStyle otherStyle;

	public OrCardStackingStyle(final CardStackingStyle style, final CardStackingStyle otherStyle) {
		this.style = style;
		this.otherStyle = otherStyle;
	}

	public boolean isSatisfiedBy(final CardStack stack) {
		return style.isSatisfiedBy(stack) || otherStyle.isSatisfiedBy(stack);
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack, StackingStyleTracker tracker) {
		if (style.isSatisfiedBy(stack)) {
			return true;
		}
		
		if (otherStyle.isSatisfiedBy(stack)) {
			return true;
		}
		
		tracker.notSatisifiedBy(otherStyle);
		
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + style.toString() + " || " + otherStyle.toString() + ")";
	}
}