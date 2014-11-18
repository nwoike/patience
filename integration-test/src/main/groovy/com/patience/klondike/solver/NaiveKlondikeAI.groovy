package com.patience.klondike.solver

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import com.patience.common.domain.model.card.PlayingCard;

public class NaiveKlondikeAI implements KlondikeAI {

	private CycleDetector cycleDetector = new CycleDetector();
	
	private List<PlayingCard> drawn = newArrayList();

	@Override
	public Move bestAvailableMove(KlondikeGameData gameData) {
		List<Move> availableMoves = availableMoves(gameData);
		return availableMoves == null || availableMoves.isEmpty() ? null : availableMoves.get(0);
	}

	@Override
	public List<Move> availableMoves(KlondikeGameData gameData) {
		if (gameData == null) return null;

		return hasCycle(gameData)
				? new ArrayList<Move>()
				: newArrayList(new GameAnalyzer(gameData).availableMoves());
	}

	private boolean hasCycle(KlondikeGameData gameData) {
		PlayingCard previousDrawn = drawn.isEmpty() ? null : drawn.get(drawn.size() - 1);
		PlayingCard currentDrawn = gameData.waste();

		if (currentDrawn != null && !currentDrawn.equals(previousDrawn)) {
			drawn.add(currentDrawn);
		}

		return cycleDetector.detect(drawn);
	}
}