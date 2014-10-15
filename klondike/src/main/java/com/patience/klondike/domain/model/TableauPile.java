package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.CardStack;
import com.patience.common.domain.model.PlayingCard;
import com.patience.common.domain.model.Rank;

public class TableauPile {

	private int tableauPileId;

	private List<PlayingCard> unflippedCards = newArrayList();

	private CardStack flippedCards;

	public TableauPile(int tableauPileId, List<PlayingCard> unflippedCards, CardStack flippedCards) {
		checkArgument(tableauPileId > 0, "Tableau Pile Id must be greater than 0.");
		checkNotNull(unflippedCards, "Unflipped cards must be provided.");
		checkNotNull(flippedCards, "Flipped cards must be provided.");

		this.tableauPileId = tableauPileId;
		this.unflippedCards.addAll(unflippedCards);
		this.flippedCards = flippedCards;
	}

	public void addCards(CardStack cards) {
		checkNotNull(cards, "Cards must be provided.");
		
		if (!isAssignable(cards.topCard())) {
			throw new IllegalArgumentException("Provided cards are not currently assignable to this TableauPile.");
		}

		this.flippedCards = flippedCards.withAdditionalCards(cards);
	}

	public void removeCards(CardStack cards) {
		checkNotNull(cards, "Cards must be provided.");
		this.flippedCards = flippedCards.withCardsRemoved(cards);
	}

	public void flipTopCard() {
		if (unflippedCards.isEmpty()) {
			throw new IllegalStateException("No flippable cards in Tableau Pile.");
		}

		PlayingCard removedCard = unflippedCards.remove(unflippedCards.size() - 1);
		this.flippedCards = new CardStack(removedCard);
	}

	/**
	 * A lane is an empty space in the tableau, which has been formed by the
	 * removal of an entire column of cards.
	 */
	public boolean isLane() {
		return unflippedCards.isEmpty() && flippedCards.cardCount() == 0;
	}

	private boolean isAssignable(PlayingCard card) {
		checkNotNull(card, "A card must be provided.");

		if (isLane() && Rank.King != card.rank()) {
			return false;
		}

		if (isFull()) {
			return false;
		}

		return true;
	}

	/**
	 * Is the last card in the Pile an Ace?
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
	
	public CardStack flippedCards() {
		return flippedCards;
	}

	public int totalCardCount() {
		return unflippedCards.size() + flippedCards.cardCount();
	}

	public int unflippedCardCount() {
		return unflippedCards.size();
	}
}