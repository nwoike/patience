package com.patience.klondike.solver;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.patience.common.domain.model.card.PlayingCard;

public class KlondikeGameData {

	private final List<PlayingCard> foundations;
	
	private final List<TableauPileData> tableauPiles;
	
	private final PlayingCard waste;
	
	private final boolean passLimitReached;
	
	public KlondikeGameData(List<PlayingCard> foundations, List<TableauPileData> tableauPiles, PlayingCard waste) {		
		this(foundations, tableauPiles, waste, false);
	}
	
	public KlondikeGameData(List<PlayingCard> foundations, List<TableauPileData> tableauPiles, 
			PlayingCard waste, boolean passLimitReached ) {		
		this.foundations = ImmutableList.copyOf(foundations);
		this.tableauPiles = ImmutableList.copyOf(tableauPiles);
		this.waste = waste;		
		this.passLimitReached = passLimitReached;
	}
	
	public List<PlayingCard> foundations() {
		return foundations;
	}
	
	public List<TableauPileData> tableauPiles() {
		return tableauPiles;
	}
	
	public PlayingCard waste() {
		return waste;
	}
	
	public boolean isPassLimitReached() {
		return passLimitReached;
	}
}
