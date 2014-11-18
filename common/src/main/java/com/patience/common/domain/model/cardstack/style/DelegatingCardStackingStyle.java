package com.patience.common.domain.model.cardstack.style;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.cardstack.CardStack;

public class DelegatingCardStackingStyle extends AbstractCardStackingStyle {

	private final CardStackingStyle cardStackingStyle;

	public DelegatingCardStackingStyle(CardStackingStyle cardStackingStyle) {
		this.cardStackingStyle = checkNotNull(cardStackingStyle);		
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack) {			
		return cardStackingStyle.isSatisfiedBy(stack);
	}
	
}
