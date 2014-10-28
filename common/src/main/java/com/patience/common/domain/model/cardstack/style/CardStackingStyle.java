package com.patience.common.domain.model.cardstack.style;

import com.patience.common.domain.model.card.CardStack;

public interface CardStackingStyle {

	boolean isSatisfiedBy(CardStack stack);

	boolean isSatisfiedBy(CardStack stack, StackingStyleTracker tracker);
	
	CardStackingStyle and(CardStackingStyle stackingStyle);

	CardStackingStyle or(CardStackingStyle stackingStyle);

	CardStackingStyle not(CardStackingStyle stackingStyle);

}