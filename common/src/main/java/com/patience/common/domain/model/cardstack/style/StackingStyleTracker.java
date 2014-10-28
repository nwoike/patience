package com.patience.common.domain.model.cardstack.style;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

public class StackingStyleTracker {

	private List<CardStackingStyle> unsatisfiedStackingStyles = newArrayList();
	
	public void notSatisifiedBy(CardStackingStyle stackingStyle) {
		this.unsatisfiedStackingStyles.add(stackingStyle);
	}
	
	public List<CardStackingStyle> unsatisfiedStackingStyles() {
		return unsatisfiedStackingStyles;
	}
}
