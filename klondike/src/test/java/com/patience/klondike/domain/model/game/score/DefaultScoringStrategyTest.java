package com.patience.klondike.domain.model.game.score;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;

public class DefaultScoringStrategyTest {

	private DefaultScoringStrategy scoringStrategy;
	
	@Before
	public void setup() {
		scoringStrategy = new DefaultScoringStrategy();
	}
	
	@Test
	public void calculatePointsNegativeReturnsZero() {
		Set<Move> moves = newHashSet();
		Set<PlayingCard> flippedCards = newHashSet();
		
		TrackedMoves trackedMoves = new TrackedMoves(moves, flippedCards, 1);		
		long points = scoringStrategy.calculatePoints(trackedMoves);
		
		assertThat(0L, equalTo(points));
	}

	@Test
	public void calculatePointsPositive() {
		Set<Move> moves = newHashSet(
			new Move(PlayingCard.KingOfClubs, PileType.Tableau, PileType.Foundation),
			new Move(PlayingCard.QueenOfHearts, PileType.Waste, PileType.Foundation),
			new Move(PlayingCard.JackOfSpades, PileType.Waste, PileType.Tableau),
			new Move(PlayingCard.TenOfDiamonds, PileType.Foundation, PileType.Tableau)
		);
		
		Set<PlayingCard> flippedCards = newHashSet(PlayingCard.AceOfDiamonds);
		
		TrackedMoves trackedMoves = new TrackedMoves(moves, flippedCards, 0);		
		long points = scoringStrategy.calculatePoints(trackedMoves);
		
		assertThat(15L, equalTo(points));
	}
}
