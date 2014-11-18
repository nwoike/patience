package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Suit;
import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class MatchingSuit extends AbstractCardStackingStyle {

	private Suit suit;

	public MatchingSuit(Suit suit) {
		this.suit = checkNotNull(suit, "Suit must be provided.");
	}
	
	public MatchingSuit() {		
	}
	
	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
		
		PlayingCard previousCard = null;
		
		for (PlayingCard playingCard : stack) {
			if (previousCard == null) {
				previousCard = playingCard;
				continue;
			}
			
			Suit match = (suit == null) ? previousCard.suit() : suit;
						
			if (!match.equals(playingCard.suit())) {
				return false;
			}
				
			previousCard = playingCard;
		}
		
		return true;
	}

}
