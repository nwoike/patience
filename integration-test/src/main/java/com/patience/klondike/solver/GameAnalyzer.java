package com.patience.klondike.solver;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Iterables;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.cardstack.CardStack;

public class GameAnalyzer {

	private final KlondikeGameData klondikeData;

	private final Set<Move> availableMoves = newHashSet();

	public GameAnalyzer(KlondikeGameData klondikeData) {
		this.klondikeData = checkNotNull(klondikeData,
				"KlondikeData must be provided");
		this.analyze();
	}

	public List<Move> availableMoves() {
		ArrayList<Move> copy = newArrayList(availableMoves);
		Collections.sort(copy);
		return copy;
	}

	private void analyze() {
		analyzePromotions();
		analyzeFlips();
		analyzeMoves();
		analyzeDraw();
	}

	private void analyzePromotions() {
		List<PlayingCard> foundations = klondikeData.foundations();
		List<TableauPileData> tableauPiles = klondikeData.tableauPiles();
		PlayingCard waste = klondikeData.waste();

		// Promote from Waste
		if (waste != null) {
			if (Rank.Ace == waste.rank()) {
				availableMoves.add(new PromoteCard(waste));
			}

			for (PlayingCard playingCard : foundations) {
				if (playingCard.suit().equals(waste.suit())
						&& playingCard.rank().precedes(waste.rank())) {
					availableMoves.add(new PromoteCard(waste));
				}
			}
		}

		// Promote from Tableau Piles
		for (TableauPileData tableauPileData : tableauPiles) {
			CardStack cardStack = tableauPileData.cardStack();
			PlayingCard tableauPileCard = cardStack.topCard();

			if (tableauPileCard == null) {
				continue;
			}

			if (!cardStack.isEmpty() && Rank.Ace == tableauPileCard.rank()) {
				availableMoves.add(new PromoteCard(tableauPileCard));
			}

			for (PlayingCard playingCard : foundations) {
				if (playingCard.suit().equals(tableauPileCard.suit())
						&& playingCard.rank().precedes(tableauPileCard.rank())) {
					availableMoves.add(new PromoteCard(tableauPileCard));
				}
			}
		}
	}

	private void analyzeFlips() {
		List<TableauPileData> tableauPiles = klondikeData.tableauPiles();

		for (int i = 0; i < tableauPiles.size(); i++) {
			TableauPileData tableauPileData = Iterables.get(tableauPiles, i);
			CardStack cardStack = tableauPileData.cardStack();

			if (tableauPileData.unflippedCardCount() != 0
					&& cardStack.isEmpty()) {
				availableMoves.add(new FlipCard(i + 1));
			}
		}
	}

	private void analyzeMoves() {
		List<TableauPileData> tableauPiles = klondikeData.tableauPiles();
		PlayingCard waste = klondikeData.waste();

		for (int i = 0; i < tableauPiles.size(); i++) {
			TableauPileData tableauPileData = tableauPiles.get(i);

			if (tableauPileData.unflippedCardCount() > 0
					&& tableauPileData.cardStack().isEmpty()) {
				continue;
			}

			CardStack cardStack = tableauPileData.cardStack();

			try {
				cardStack.withAdditionalCard(waste);
				availableMoves.add(new MoveCard(PileType.Waste, waste, i + 1));

			} catch (IllegalArgumentException e) {
				continue;
			}
		}

		// Across Tableau Piles
		for (int i = 0; i < tableauPiles.size(); i++) {
			for (int j = 0; j < tableauPiles.size(); j++) {
				if (i == j)
					continue;

				TableauPileData originTableauPile = tableauPiles.get(i);
				TableauPileData destinationTableauPile = tableauPiles.get(j);

				if (destinationTableauPile.unflippedCardCount() > 0
						&& destinationTableauPile.cardStack().isEmpty()) {
					continue;
				}

				CardStack originStack = originTableauPile.cardStack();
				CardStack destinationStack = destinationTableauPile.cardStack();

				if (originStack.isEmpty()) {
					continue;
				}

				if (Rank.King.equals(originStack.bottomCard().rank())
						&& originTableauPile.unflippedCardCount() == 0
						&& destinationTableauPile.unflippedCardCount() == 0) {
					continue;
				}

				try {
					destinationStack.withAdditionalCards(originStack);
					availableMoves.add(new MoveCard(PileType.TableauPile,
							originStack.bottomCard(), j + 1));

				} catch (IllegalArgumentException e) {
					continue;
				}
			}
		}
	}

	private void analyzeDraw() {
		if (klondikeData.isPassLimitReached()) {
			return;
		}

		availableMoves.add(new DrawCard());
	}
}