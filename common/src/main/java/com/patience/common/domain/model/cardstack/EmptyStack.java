package com.patience.common.domain.model.cardstack;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.specification.AbstractCardStackingStyle;

public class EmptyStack extends AbstractCardStackingStyle {

	@Override
	public boolean isSatisfiedBy(CardStack stack) {		
		return stack.isEmpty();
	}

}
