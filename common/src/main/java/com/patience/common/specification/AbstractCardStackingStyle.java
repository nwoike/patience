package com.patience.common.specification;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.CardStack;

public abstract class AbstractCardStackingStyle implements CardStackingStyle {

	public abstract boolean isSatisfiedBy(CardStack stack);

	@Override
	public boolean isSatisfiedBy(CardStack stack, StackingStyleTracker tracker) {
		checkNotNull(tracker, "Tracker must be provided.");
		
		final boolean satisfaction = isSatisfiedBy(stack);
		
		if (!satisfaction) {
			tracker.notSatisifiedBy(this);
		}
		
		return satisfaction;
	}
	
	public CardStackingStyle and(final CardStackingStyle specification) {
		return new AndCardStackingStyle(this, specification);
	}

	public CardStackingStyle or(final CardStackingStyle specification) {
		return new OrCardStackingStyle(this, specification);
	}

	public CardStackingStyle not(final CardStackingStyle specification) {
		return new NotCardStackingStyle(specification);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}