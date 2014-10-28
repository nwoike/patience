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
import com.patience.common.domain.model.cardstack.BottomRank;
import com.patience.common.domain.model.cardstack.DecreasingRank;
import com.patience.common.domain.model.cardstack.EmptyStack;
import com.patience.common.domain.model.cardstack.SequentialRank;
import com.patience.common.domain.model.cardstack.style.CardStackingStyle;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.domain.model.game.score.PileType;

public final class TableauPile implements Pile {

	private int tableauPileId;

	private CardStack unflippedCards;

	private CardStack flippedCards;
	
	private static final CardStackingStyle unflippedStackingStyle = 
			new EmptyStack()
				.or(new AnyOrder());
	
	private static final CardStackingStyle flippedStackingStyle =
			new EmptyStack()
				.or(new AlternatingSuitColor()
					 .and(new DecreasingRank())
					 .and(new SequentialRank()));
	
	private static final CardStackingStyle laneStackingStyle =
			flippedStackingStyle
				.and(new BottomRank(Rank.King));
	
	public TableauPile(int tableauPileId, List<PlayingCard> unflippedCards, List<PlayingCard> flippedCards) {
		this.setTableauPileId(tableauPileId);
		this.setUnflippedCards(unflippedCards);
		this.setFlippedCards(flippedCards);
	}

	public void addCards(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");		
		CardStack newFlippedCards = flippedCards.withAdditionalCards(cards);		
		this.flippedCards = new CardStack(newFlippedCards, determineFlippedStackingStyle());
	}

	public void removeCards(List<PlayingCard> cards) {
		checkNotNull(cards, "Cards must be provided.");
		this.flippedCards = new CardStack(flippedCards.withCardsRemoved(cards), determineFlippedStackingStyle());
	}

	public void removeTopCard(PlayingCard card) {
		removeCards(newArrayList(card));
	}
	
	public PlayingCard removeTopCard() {
		PlayingCard topCard = flippedCards.topCard();
		removeCards(newArrayList(topCard));
		
		return topCard;
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

	public int tableauPileId() {
		return tableauPileId;
	}

	public List<PlayingCard> unflippedCards() {
		return newArrayList(unflippedCards);
	}
	
	public CardStack flippedCards() {
		return flippedCards;
	}

	public boolean topCardsMatch(List<PlayingCard> cards) {
		return flippedCards.endsWith(cards);
	}
	
	public int totalCardCount() {
		return unflippedCards.cardCount() + flippedCards.cardCount();
	}

	public int unflippedCardCount() {
		return unflippedCards.cardCount();
	}
	
	@Override
	public PileType pileType() {
		return PileType.Tableau;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && this.getClass() == obj.getClass()) {
			TableauPile other = (TableauPile) obj;
			return tableauPileId == other.tableauPileId();
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 6007 * tableauPileId;
	}
	
	private void setTableauPileId(int tableauPileId) {
		checkArgument(tableauPileId > 0, "Tableau Pile Id must be greater than 0.");
		checkArgument(tableauPileId < 8, "Tableau Pile Id must be less than 8.");
		
		this.tableauPileId = tableauPileId;
	}
	
	private void setFlippedCards(List<PlayingCard> flippedCards) {
		checkNotNull(flippedCards, "Flipped card list must be provided but may be empty.");
		this.flippedCards = new CardStack(flippedCards, flippedStackingStyle);
	}
	
	private void setUnflippedCards(List<PlayingCard> unflippedCards) {
		checkNotNull(unflippedCards, "Unflipped card list must be provided but may be empty.");
		this.unflippedCards = new CardStack(unflippedCards, unflippedStackingStyle);
	}

	private CardStackingStyle determineFlippedStackingStyle() {
		return isLane() ? laneStackingStyle : flippedStackingStyle;
	}
}