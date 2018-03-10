package com.patience.klondike.domain.service.game;

import static org.assertj.core.util.Lists.newArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.klondike.domain.model.game.DrawCount;
import com.patience.klondike.domain.model.game.Foundation;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.PassCount;
import com.patience.klondike.domain.model.game.Settings;
import com.patience.klondike.domain.model.game.Stock;
import com.patience.klondike.domain.model.game.TableauPile;
import com.patience.klondike.domain.model.game.Waste;

public class SimpleWinCheckerTest {

    @Test
    public void isWinnableFalseNewGame() {
        WinChecker checker = new SimpleWinChecker();

        GameId gameId = new GameId(UUID.randomUUID());
        Game game = new Game(gameId, new Settings(DrawCount.One, PassCount.Unlimited));
        
        assertFalse(checker.isWinnable(game));
    }
    
    @Test
    public void isWinnableTrueWinnableGame() {
        WinChecker checker = new SimpleWinChecker();

        GameId gameId = new GameId(UUID.randomUUID());
        
        Settings settings = new Settings(DrawCount.One, PassCount.Unlimited);
        Stock stock = new Stock(new ArrayList<PlayingCard>(), PassCount.Unlimited);
        Waste waste = new Waste(new ArrayList<PlayingCard>());
        
        List<Foundation> foundations = newArrayList();
        List<TableauPile> tableauPiles = new ArrayList<>();
        
        Game game = new Game(gameId, settings, stock, waste, foundations, tableauPiles);
        
        assertTrue(checker.isWinnable(game));
    }
    
    @Test
    public void isWinnableFalseUnflippedCardInTableau() {
        WinChecker checker = new SimpleWinChecker();

        GameId gameId = new GameId(UUID.randomUUID());
        
        Settings settings = new Settings(DrawCount.One, PassCount.Unlimited);
        Stock stock = new Stock(new ArrayList<>(), PassCount.Unlimited);
        Waste waste = new Waste(new ArrayList<>());
        
        TableauPile tableauPile = new TableauPile(1, newArrayList(PlayingCard.AceOfClubs), new ArrayList<>());
        
        List<Foundation> foundations = new ArrayList<>();
        List<TableauPile> tableauPiles = newArrayList(tableauPile);
        
        Game game = new Game(gameId, settings, stock, waste, foundations, tableauPiles);
        
        assertFalse(checker.isWinnable(game));
    }

}
