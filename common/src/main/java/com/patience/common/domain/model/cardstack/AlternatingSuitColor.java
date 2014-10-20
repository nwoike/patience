package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.specification.AbstractCardStackingStyle;

public class AlternatingSuitColor extends AbstractCardStackingStyle {

	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
		
		PlayingCard previousCard = null;
		
		for (PlayingCard playingCard : stack) {
			if (previousCard == null) {
				previousCard = playingCard;
				continue;
			}
			
			if (playingCard.suit().color().equals(previousCard.suit().color())) {
				return false;
			}			
			
			previousCard = playingCard;
		}
		
		return true;
	}

}