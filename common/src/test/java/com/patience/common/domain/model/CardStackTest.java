package com.patience.common.domain.model;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CardStackTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void emptycardStack() {
		CardStack cardStack = CardStack.emptyStack();
		CardStack other = CardStack.emptyStack();

		assertThat(cardStack.cards().size(), equalTo(0));
		assertThat(other, sameInstance(other));
	}

	@Test
	public void cardStackWith2ValidPlayingCards() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);

		CardStack cardStack = new CardStack(expected);

		assertThat(cardStack.cards(), equalTo(expected));
	}

	@Test
	public void cardStackWith3ValidPlayingCards() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);
		CardStack cardStack = new CardStack(expected);

		assertThat(cardStack.cards(), equalTo(expected));
	}

	@Test
	public void cardStackWithIncompatibleSuits() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided cards do not form a valid stack. Ten of Clubs and Nine of Clubs are not of alternating suits.");

		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfClubs);

		new CardStack(expected);

	}

	@Test
	public void cardStackWithIncompatibleRanks() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided cards do not form a valid stack. Ten of Clubs and Eight of Diamonds are not in decreasing rank.");

		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.EightOfDiamonds);

		new CardStack(expected);
	}

	@Test
	public void cardStackWithIncompatibleSuitsAndRanks() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided cards do not form a valid stack. Nine of Diamonds and Seven of Hearts are not of alternating suits.");

		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.SevenOfHearts, PlayingCard.AceOfClubs);
		new CardStack(expected);
	}

	@Test
	public void cardStackAddAdditionalValidPlayingCard() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);

		CardStack cardStack = new CardStack(expected);
		CardStack withAdditionalCards = cardStack.withAdditionalCards(PlayingCard.EightOfClubs);

		assertThat(withAdditionalCards, not(equalTo(cardStack)));
	}

	@Test
	public void cardStackAddAdditionalInvalidPlayingCard() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided cards do not form a valid stack. Nine of Diamonds and Eight of Diamonds are not of alternating suits.");

		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);
		CardStack cardStack = new CardStack(expected);

		cardStack.withAdditionalCards(PlayingCard.EightOfDiamonds);
	}

	@Test
	public void cardStackAddAdditionalValidPlayingCards() {
		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);
		CardStack cardStack = new CardStack(expected);

		CardStack withAdditionalCards = cardStack.withAdditionalCards(cards(PlayingCard.EightOfClubs, PlayingCard.SevenOfHearts));
		
		assertThat(withAdditionalCards, not(equalTo(cardStack)));
	}

	@Test
	public void cardStackAddAdditionalInvalidPlayingCards() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided cards do not form a valid stack. Eight of Clubs and Five of Hearts are not in decreasing rank.");

		List<PlayingCard> expected = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds);
		CardStack cardStack = new CardStack(expected);

		cardStack.withAdditionalCards(cards(PlayingCard.EightOfClubs, PlayingCard.FiveOfHearts));
	}

	@Test
	public void cardStackWithValidPlayingCardsRemoved() {
		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);

		CardStack cardStack = new CardStack(cards);
		CardStack expected = new CardStack(cards(PlayingCard.TenOfClubs));

		CardStack withCardsRemoved = cardStack.withCardsRemoved(cards(PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs));

		assertThat(expected, equalTo(withCardsRemoved));
	}

	@Test
	public void cardStackWithInvalidPlayingCardsRemoved() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Provided CardStack is not removable.");

		List<PlayingCard> cards = cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds, PlayingCard.EightOfClubs);

		CardStack cardStack = new CardStack(cards);

		cardStack.withCardsRemoved((cards(PlayingCard.TenOfClubs, PlayingCard.NineOfDiamonds)));
	}

	private List<PlayingCard> cards(PlayingCard... cards) {
		return Arrays.asList(cards);
	}
}