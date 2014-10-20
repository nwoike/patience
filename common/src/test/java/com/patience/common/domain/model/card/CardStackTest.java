package com.patience.common.domain.model.card;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.patience.common.domain.model.cardstack.MatchingSuit;
import com.patience.common.specification.CardStackingStyle;

public class CardStackTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void cardStackWith2Cards() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);		
		CardStack cardStack = new CardStack(expected);
	
		assertThat(cardStack.cards(), equalTo(expected));
	}	

	@Test
	public void cardStackWith3Cards() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);		
		CardStack cardStack = new CardStack(expected);
	
		assertThat(cardStack.cards(), equalTo(expected));
	}
	
	@Test
	public void cardStackAddAdditionalCard() {
		CardStack cardStack = new CardStack(cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds));
		CardStack withAdditionalCards = cardStack.withAdditionalCard(PlayingCard.EightOfClubs);

		CardStack expected = new CardStack(cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs));
		
		assertThat(expected, equalTo(withAdditionalCards));
	}
	
	@Test
	public void cardStackAddAdditionalCards() {
		CardStack cardStack = new CardStack(cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds));
		CardStack withAdditionalCards = cardStack.withAdditionalCards(cards(PlayingCard.EightOfClubs, PlayingCard.SevenOfHearts));
		
		CardStack expected = new CardStack(cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs, PlayingCard.SevenOfHearts));
		
		assertThat(withAdditionalCards, equalTo(expected));
	}
	
	@Test
	public void cardStackWithCardsRemoved() {
		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);		
		CardStack cardStack = new CardStack(cards);
	
		CardStack expected = new CardStack(cards(PlayingCard.TenOfClubs));
		CardStack withCardsRemoved = cardStack.withCardsRemoved((cards(PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs)));
		
		assertThat(withCardsRemoved, equalTo(expected));
	}
	
	@Test
	public void endsWithSelf() {
		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);		
		CardStack cardStack = new CardStack(cards);
		assertTrue(cardStack.endsWith(cardStack));
	}
	
	@Test
	public void endsWithSubset() {
		CardStack cardStack = new CardStack(cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs));
		CardStack otherStack = new CardStack(cards(PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs));
				
		assertTrue(cardStack.endsWith(otherStack));
	}
	
	@Test
	public void withValidStackingStyle() {
		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfClubs, PlayingCard.EightOfClubs);
		CardStackingStyle stackingStyle = new MatchingSuit(Suit.Clubs);
		CardStack cardStack = new CardStack(cards, stackingStyle);
		
		assertNotNull(cardStack);
	}
	
	// Test Adding and Removing cards from CardStack with Valid and Invalid Stacking Styles
	
	@Test(expected=IllegalArgumentException.class)
	public void withInvalidStackingStyle() {
		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.EightOfHearts, PlayingCard.EightOfClubs);
		CardStackingStyle stackingStyle = new MatchingSuit(Suit.Clubs);
		new CardStack(cards, stackingStyle);
	}
	
	private List<PlayingCard> cards(PlayingCard... cards) {
		return Arrays.asList(cards);
	}
}