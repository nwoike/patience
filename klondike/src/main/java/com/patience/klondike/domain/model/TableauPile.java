package com.patience.klondike.domain.model;

import static com.google.common.base.Preconditions.checkArgument;

public class TableauPile {

	private int tableauPileId;

	public TableauPile(int tableauPileId) {
		checkArgument(tableauPileId > 0, "Tableau Pile Id must be greater than 0.");
		this.tableauPileId = tableauPileId;		
	}
	
	public int tableauPileId() {
		return tableauPileId;
	}
}
