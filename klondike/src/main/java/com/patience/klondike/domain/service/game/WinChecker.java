package com.patience.klondike.domain.service.game;

import com.patience.klondike.domain.model.game.Game;

public interface WinChecker {

	boolean isWinnable(Game game);

}