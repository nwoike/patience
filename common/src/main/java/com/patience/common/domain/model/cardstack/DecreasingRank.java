package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class DecreasingRank extends AbstractCardStackingStyle {

	@Override
	public boolean isSatisfiedBy(CardStack stack) {		
		checkNotNull(stack, "CardStack must be provided.");
		
		List<PlayingCard> playingCards = stack.cards();
		PlayingCard previousCard = playingCards.get(0);
		
		for (int i = 1; i < playingCards.size(); i++) {
			PlayingCard currentCard = playingCards.get(i);
			
			if (currentCard.rank().higherThan(previousCard.rank())) { 
				return false;
			}	
			
			previousCard = currentCard;
		}
		
		return true;
	}

}