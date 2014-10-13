package com.patience.common.domain.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SuitTest {
	
	@Test
	public void clubsIsAlternateOfHearts() {
		assertTrue(Suit.Clubs.alternatingColorOf(Suit.Hearts));
	}
	
	@Test
	public void spadesIsAlternateOfHearts() {
		assertTrue(Suit.Spades.alternatingColorOf(Suit.Hearts));
	}
	
	@Test
	public void clubsIsAlternateOfDiamonds() {
		assertTrue(Suit.Clubs.alternatingColorOf(Suit.Diamonds));
	}
	
	@Test
	public void spadesIsAlternateOfDiamonds() {
		assertTrue(Suit.Spades.alternatingColorOf(Suit.Diamonds));
	}
	
	@Test
	public void heartsIsAlternateOfClubs() {
		assertTrue(Suit.Hearts.alternatingColorOf(Suit.Clubs));
	}
	
	@Test
	public void heartsIsAlternateOfSpades() {
		assertTrue(Suit.Hearts.alternatingColorOf(Suit.Spades));
	}
	
	@Test
	public void diamondsIsAlternateOfClubs() {
		assertTrue(Suit.Diamonds.alternatingColorOf(Suit.Clubs));
	}
	
	@Test
	public void diamondsIsAlternateOfSpades() {
		assertTrue(Suit.Diamonds.alternatingColorOf(Suit.Spades));
	}
}