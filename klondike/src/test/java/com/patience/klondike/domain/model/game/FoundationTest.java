package com.patience.klondike.domain.model.game;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;

public class FoundationTest {

	@Test
	public void constructorId() {
		Foundation foundation = new Foundation(1);
		assertNotNull(foundation);
	}

	@Test
	public void addValidFirstCardAce() {
		Foundation foundation = new Foundation(1);
		foundation.promoteCard(PlayingCard.AceOfClubs);

		assertThat(newArrayList(PlayingCard.AceOfClubs), equalTo(foundation.cards()));
	}
	
	@Test
	public void addValidAdditionalCardMatchesSuit() {
		Foundation foundation = new Foundation(1);
		foundation.promoteCard(PlayingCard.AceOfClubs);
		foundation.promoteCard(PlayingCard.TwoOfClubs);
		
		assertThat(newArrayList(PlayingCard.AceOfClubs, PlayingCard.TwoOfClubs), equalTo(foundation.cards()));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addInvalidFirstCardKing() {
		Foundation foundation = new Foundation(1);
		foundation.promoteCard(PlayingCard.KingOfClubs);
	}
}
