package com.patience.klondike.domain.model.game;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.card.CardStack;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.cardstack.AlternatingSuitColor;
import com.patience.common.domain.model.cardstack.AnyOrder;
import com.patience.common.domain.model.cardstack.DecreasingRank;
import com.patience.common.domain.model.cardstack.EmptyStack;
import com.patience.common.domain.model.cardstack.SequentialRank;
import com.patience.common.specification.CardStackingStyle;
import com.patience.klondike.application.IllegalMoveException;

public class TableauPile {

	private int tableauPileId;

	private CardStack unflippedCards;

	private CardStack flippedCards;
	
	private static CardStackingStyle unflippedStackingStyle = 
			new AnyOrder();
	
	private static CardStackingStyle flippedStackingStyle =
			new EmptyStack()
				.or(new AlternatingSuitColor()
					 .and(new DecreasingRank())
					 .and(new SequentialRank()));
	
	public TableauPile(int tableauPileId, List<PlayingCard> unflippedCards, List<PlayingCard> flippedCards) {
		this.setTableauPileId(tableauPileId);
		this.setUnflippedCards(unflippedCards);		
		this.setFlippedCards(flippedCards);
		//this.checkAssignability(flippedCards());
	}

	public void addCards(CardStack cards) {
		checkNotNull(cards, "Cards must be provided.");
		
		this.checkAssignability(cards);
		this.flippedCards = flippedCards.withAdditionalCards(cards);
	}

	public void removeCards(CardStack cards) {
		checkNotNull(cards, "Cards must be provided.");
		this.flippedCards = flippedCards.withCardsRemoved(cards);
	}

	public void flipTopCard() {
		if (unflippedCards.isEmpty()) {
			throw new IllegalMoveException("No unflipped cards were found in Tableau Pile.");
		}

		PlayingCard topCard = unflippedCards.topCard();
		this.unflippedCards = unflippedCards.withCardRemoved(topCard);				
		this.flippedCards = flippedCards.withAdditionalCard(topCard);
	}

	/**
	 * A lane is an empty space in the tableau, which has been formed by the
	 * removal of an entire column of cards.
	 */
	public boolean isLane() {
		return unflippedCards.isEmpty() && flippedCards.isEmpty();
	}
	
	private void checkAssignability(CardStack cardStack) {
		if (!isAssignable(cardStack)) {
			throw new IllegalMoveException("Provided cards are not assignable to this TableauPile.");
		}
	}
	
	private boolean isAssignable(CardStack cardStack) {
		checkNotNull(cardStack, "CardStack must be provided.");

		if (isLane() && Rank.King != cardStack.bottomCard().rank()) {
			return false;
		}

		if (isFull()) {
			return false;
		}
		
		return true;
	}

	public boolean contains(CardStack stack) {
		return flippedCards.contains(stack);
	}
	
	/**
	 * Is the last flipped card in the Pile an Ace?
	 */
	private boolean isFull() {
		if (isLane()) {
			return false;
		}

		return flippedCards.topCard().rank() == Rank.Ace;
	}

	public int tableauPileId() {
		return tableauPileId;
	}

	public List<PlayingCard> unflippedCards() {
		return newArrayList(unflippedCards);
	}
	
	public CardStack flippedCards() {
		return flippedCards;
	}

	public boolean topCardMatches(PlayingCard other) {
		checkNotNull(other, "Playing card must be provided.");
		return other.equals(flippedCards.topCard());
	}
	
	public int totalCardCount() {
		return unflippedCards.cardCount() + flippedCards.cardCount();
	}

	public int unflippedCardCount() {
		return unflippedCards.cardCount();
	}
	
	private void setTableauPileId(int tableauPileId) {
		checkArgument(tableauPileId > 0, "Tableau Pile Id must be greater than 0.");
		checkArgument(tableauPileId < 8, "Tableau Pile Id must be less than 8.");
		
		this.tableauPileId = tableauPileId;
	}
	
	private void setFlippedCards(List<PlayingCard> flippedCards) {
		checkNotNull(flippedCards, "Flipped cards must be provided.");		
		this.flippedCards = new CardStack(flippedCards, flippedStackingStyle);	
	}

	private void setUnflippedCards(List<PlayingCard> unflippedCards) {
		checkNotNull(unflippedCards, "Unflipped cards must be provided.");
		this.unflippedCards = new CardStack(unflippedCards, unflippedStackingStyle);	
	}
}