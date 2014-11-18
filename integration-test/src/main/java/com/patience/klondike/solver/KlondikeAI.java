package com.patience.klondike.solver;

import java.util.List;

public interface KlondikeAI {

	Move bestAvailableMove(KlondikeGameData gameData);
	
	List<Move> availableMoves(KlondikeGameData gameData);

}