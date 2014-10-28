package com.patience.common.domain.model.cardstack;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.style.AbstractCardStackingStyle;

public class SequentialRank extends AbstractCardStackingStyle {

	@Override
	public boolean isSatisfiedBy(CardStack stack) {
		checkNotNull(stack, "CardStack must be provided.");
		
		List<PlayingCard> playingCards = stack.cards();
		
		if (playingCards.isEmpty() || playingCards.size() == 1) {
			return true;
		}
		
		PlayingCard previousCard = playingCards.get(0);
		PlayingCard nextCard = playingCards.get(1);
		
		if (previousCard.rank().follows(nextCard.rank())) {
			for (int i = 1; i < playingCards.size(); i++) {
				PlayingCard currentCard = playingCards.get(i);
				
				if (currentCard.rank().follows(previousCard.rank())) {
					return false;
				}
				
				previousCard = currentCard;
			}
			
			return true;
			
		} else if (nextCard.rank().follows(previousCard.rank())) {
			for (int i = 1; i < playingCards.size(); i++) {
				PlayingCard currentCard = playingCards.get(i);
				
				if (previousCard.rank().follows(currentCard.rank())) {
					return false;
				}
				
				previousCard = currentCard;
			}
			
			return true;
		}
		
		return false;
	}
}