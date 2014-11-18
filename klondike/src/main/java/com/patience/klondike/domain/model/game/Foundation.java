package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.card.Suit;
import com.patience.common.domain.model.cardstack.BottomRank;
import com.patience.common.domain.model.cardstack.CardStack;
import com.patience.common.domain.model.cardstack.EmptyStack;
import com.patience.common.domain.model.cardstack.IncreasingRank;
import com.patience.common.domain.model.cardstack.MatchingSuit;
import com.patience.common.domain.model.cardstack.SequentialRank;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.domain.model.game.score.PileType;

public final class Foundation implements Pile {
	
	private int foundationId;
	
	private CardStack cardStack;
		
	private static CardStackingStyle foundationStackingStyle = 
			new EmptyStack()
				.or(new MatchingSuit()
					 .and(new BottomRank(Rank.Ace))
					 .and(new IncreasingRank())
					 .and(new SequentialRank()));
	
	public Foundation(int foundationId) {
		this(foundationId, new ArrayList<PlayingCard>());
	}
	
	public Foundation(int foundationId, List<PlayingCard> playingCards) {
		checkNotNull(playingCards, "Playing cards must be provided.");
		
		this.setFoundationId(foundationId);
		this.setCardStack(playingCards);
	}

	public void promoteCard(PlayingCard playingCard) {
		checkNotNull(playingCard, "Playing card must be provided.");		
		this.cardStack = cardStack.withAdditionalCard(playingCard);	
	}
	
	@Override
	public void removeCards(List<PlayingCard> cards) {
		if (cards.size() > 1) {
			throw new IllegalMoveException("Can only remove a single card from a foundation at a time.");
		}
		
		removeTopCard();
	}
	
	public PlayingCard removeTopCard() {
		if (cardStack.isEmpty()) {
			throw new IllegalMoveException("Foundation is already empty.");
		}
		
		PlayingCard topCard = cardStack.topCard();
		this.cardStack = this.cardStack.withCardRemoved(topCard);
		
		return topCard;
	}
	
	public int foundationId() {
		return foundationId;
	}
	
	public boolean hasAssignedSuit() {
		return suit() != null;
	}
	
	public Suit suit() {
		return isEmpty() ? null : topCard().suit();
	}
	
	public PlayingCard topCard() {
		return cardStack.topCard();
	}
	
	public boolean topCardsMatch(List<PlayingCard> cards) {
		return cardStack.endsWith(cards);
	}
	
	public boolean isEmpty() {
		return cardStack.isEmpty();
	}
	
	public List<PlayingCard> cards() {		
		return cardStack.cards();
	}
	
	@Override
	public PileType pileType() {
		return PileType.Foundation;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			Foundation other = (Foundation) obj;
			return foundationId == other.foundationId();
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {		
		return 6133 * foundationId;
	}
	
	private void setFoundationId(int foundationId) {
		checkArgument(foundationId > 0, "Foundation Id must be greater than 0.");
		checkArgument(foundationId < 5, "Foundation Id must be less than 5.");
		
		this.foundationId = foundationId;
	}
	
	private void setCardStack(List<PlayingCard> playingCards) {
		checkNotNull(playingCards, "PlayingCards must be provided.");
		this.cardStack = new CardStack(playingCards, foundationStackingStyle);
	}
}