package com.patience.common.domain.model.card;

import static org.junit.Assert.*;

import org.junit.Test;

import com.patience.common.domain.model.card.Rank;

public class RankTest {

	@Test
	public void follows() {
		assertTrue(Rank.Four.follows(Rank.Three));
	}

	@Test
	public void precedes() {
		assertTrue(Rank.Three.precedes(Rank.Four));
	}

}
