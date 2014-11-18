package com.patience.common.domain.model.cardstack.style;

import static com.patience.common.domain.model.card.PlayingCard.JackOfSpades;
import static com.patience.common.domain.model.card.PlayingCard.KingOfSpades;
import static com.patience.common.domain.model.card.PlayingCard.QueenOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.TenOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.TenOfDiamonds;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.patience.common.domain.model.cardstack.CardStack;
import com.patience.common.domain.model.cardstack.SequentialRank;

public class SequentialRankTest {

	@Test
	public void isSatisfiedByCardStackDecreasingTrue() {
		CardStack cardStack = new CardStack(KingOfSpades, QueenOfDiamonds, JackOfSpades, TenOfDiamonds);
		assertTrue(new SequentialRank().isSatisfiedBy(cardStack));
	}
	
	@Test
	public void isSatisfiedByCardStackIncreasingTrue() {
		CardStack cardStack = new CardStack(TenOfDiamonds, JackOfSpades, QueenOfDiamonds, KingOfSpades);
		assertTrue(new SequentialRank().isSatisfiedBy(cardStack));
	}
	
	@Test
	public void isSatisfiedByCardStackDecreasingFalse() {
		CardStack cardStack = new CardStack(KingOfSpades, QueenOfDiamonds, JackOfSpades, TenOfDiamonds, TenOfClubs);
		assertFalse(new SequentialRank().isSatisfiedBy(cardStack));
	}
	
	@Test
	public void isSatisfiedByCardStackIncreasingFalse() {
		CardStack cardStack = new CardStack(TenOfClubs, TenOfDiamonds, JackOfSpades, QueenOfDiamonds, KingOfSpades);
		assertFalse(new SequentialRank().isSatisfiedBy(cardStack));
	}

}
