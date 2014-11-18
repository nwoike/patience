package com.patience.klondike.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class DrawCardTest {

	@Test
	public void equalsIsTrue() {
		assertEquals(new DrawCard(), new DrawCard());
	}
	
	@Test
	public void equalsIsFalseNull() {
		assertFalse(new DrawCard().equals(null));
	}
	
	@Test
	public void equalsIsFalseObject() {
		assertFalse(new DrawCard().equals(new Object()));
	}
}