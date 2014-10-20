package com.patience.klondike.domain.model.game;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;

public class DeckTest {

	@Test
	public void deck() {
		Deck deck = new Deck();
		assertThat(deck.remainingCardCount(), equalTo(52));
	}

	@Test
	public void drawCard() {
		Deck deck = new Deck();
		deck.shuffle(1);
		
		PlayingCard card = deck.drawCard();
				
		assertThat(card, equalTo(PlayingCard.SixOfDiamonds));
		assertThat(deck.remainingCardCount(), equalTo(51));
	}
	
	@Test
	public void shuffle() {
		Deck deck = new Deck();

		Deck other = new Deck();
		deck.shuffle(1);
		
		assertThat(deck.cards(), not(equalTo(other.cards())));
	}
}
