package com.patience.klondike.domain.model.game;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;

public class TableauPileTest {

	@Test
	public void acceptsSingleFlippedCard() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList(PlayingCard.FiveOfDiamonds);
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		assertNotNull(tableauPile);
	}

	@Test
	public void acceptsSingleUnflippedAndFlippedCard() {
		List<PlayingCard> unflippedCards = newArrayList(PlayingCard.ThreeOfClubs);
		List<PlayingCard> flippedCards = newArrayList(PlayingCard.FiveOfDiamonds);
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		assertNotNull(tableauPile);
	}

	@Test
	public void acceptsOnlySingleUnflippedCard() {
		List<PlayingCard> unflippedCards = newArrayList(PlayingCard.ThreeOfClubs);
		List<PlayingCard> flippedCards = newArrayList();
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		assertNotNull(tableauPile);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void doesNotAcceptFlippedSequntialSuitsOnConstruction() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList(PlayingCard.FiveOfDiamonds, PlayingCard.FourOfDiamonds);
		
		new TableauPile(1, unflippedCards, flippedCards);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void doesNotAcceptFlippedNonSequentialAlternateSuitsOnConstruction() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList(PlayingCard.FiveOfDiamonds, PlayingCard.ThreeOfClubs);
		
		new TableauPile(1, unflippedCards, flippedCards);
	}
	
	@Test
	public void acceptsFlippedSequentialAlternateSuitsOnConstruction() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList(PlayingCard.FiveOfDiamonds, PlayingCard.FourOfClubs);
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		assertNotNull(tableauPile);
	}

	@Test
	public void acceptsAddingKingToLane() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList();
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		tableauPile.addCards(newArrayList(PlayingCard.KingOfClubs));
		
		assertThat(tableauPile.unflippedCards(), is(empty()));
		assertThat(tableauPile.flippedCards(), contains(PlayingCard.KingOfClubs));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void doesNotAcceptAddingNonKingToLane() {
		List<PlayingCard> unflippedCards = newArrayList();
		List<PlayingCard> flippedCards = newArrayList();
		
		TableauPile tableauPile = new TableauPile(1, unflippedCards, flippedCards);
		tableauPile.addCards(newArrayList(PlayingCard.AceOfClubs));
	}

}
