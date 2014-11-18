package com.patience.klondike.solver;

import static com.google.common.base.Preconditions.checkNotNull;

import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.cardstack.AlternatingSuitColor;
import com.patience.common.domain.model.cardstack.BottomRank;
import com.patience.common.domain.model.cardstack.CardStack;
import com.patience.common.domain.model.cardstack.DecreasingRank;
import com.patience.common.domain.model.cardstack.SequentialRank;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;

public class TableauPileData {

	private final int unflippedCardCount;
	
	private final CardStack cardStack;
	
	// TODO: Remove duplication with stacking styles in common jar
	private static final CardStackingStyle flippedStackingStyle =
			new AlternatingSuitColor()
				 .and(new DecreasingRank())
				 .and(new SequentialRank());
	
	private static final CardStackingStyle laneStackingStyle =
			flippedStackingStyle
				.and(new BottomRank(Rank.King));
	
	public TableauPileData(CardStack cardStack, int unflippedCardCount) {
		checkNotNull(cardStack, "CardStack must be provided.");
		this.cardStack = cardStack.isEmpty() ? new CardStack(laneStackingStyle) : new CardStack(cardStack, flippedStackingStyle);
		this.unflippedCardCount = unflippedCardCount;		
	}
	
	public CardStack cardStack() {
		return cardStack;
	}
	
	public int unflippedCardCount() {
		return unflippedCardCount;
	}
}