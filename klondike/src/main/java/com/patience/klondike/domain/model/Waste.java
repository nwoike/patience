package com.patience.klondike.domain.model;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.google.common.collect.Lists;
import com.patience.common.domain.model.PlayingCard;

public class Waste {

	private List<PlayingCard> cards = newArrayList();
	
	public Waste() {		
	}
	
	public List<PlayingCard> cards() {
		return Lists.newArrayList(cards);
	}
}
