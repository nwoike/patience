package com.patience.common.domain.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlayingCardTest {

	@Test
	public void aceOfClubs() {
		PlayingCard aceOfClubs = PlayingCard.AceOfClubs;
		assertThat(Rank.Ace, equalTo(aceOfClubs.rank()));
		assertThat(Suit.Clubs, equalTo(aceOfClubs.suit()));
		assertThat("Ace of Clubs", equalTo(aceOfClubs.toString()));
	}

}
