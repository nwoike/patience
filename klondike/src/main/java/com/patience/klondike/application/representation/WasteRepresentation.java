package com.patience.klondike.application.representation;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.patience.common.domain.model.PlayingCard;
import com.patience.klondike.domain.model.Waste;

public class WasteRepresentation {

	private List<PlayingCardRepresentation> cards = newArrayList();

	public WasteRepresentation(Waste waste) {
		for (PlayingCard card : waste.cards()) {
			cards.add(new PlayingCardRepresentation(card));
		}		
	}
	
	public List<PlayingCardRepresentation> getCards() {		
		return cards;
	}
}
