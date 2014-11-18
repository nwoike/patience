package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.SuitColor;
import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class MatchingSuitColor extends AbstractCardStackingStyle {

	private SuitColor cardColor;

	public MatchingSuitColor(SuitColor cardColor) {
		this.cardColor = checkNotNull(cardColor, "CardColor must be provided.");
	}

	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
		
		for (PlayingCard playingCard : stack) {
			if (!cardColor.equals(playingCard.suit().color())) {
				return false;
			}
		}
				
		return true;
	}
}
