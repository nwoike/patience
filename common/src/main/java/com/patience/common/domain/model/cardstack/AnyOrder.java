package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class AnyOrder extends AbstractCardStackingStyle {

	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
		return true;
	}

}
