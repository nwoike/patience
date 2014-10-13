package com.patience.common.domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class RankTest {

	@Test
	public void follows() {
		assertTrue(Rank.Four.follows(Rank.Three));
	}

	@Test
	public void precedes() {
		assertTrue(Rank.Three.preceds(Rank.Four));
	}

}
