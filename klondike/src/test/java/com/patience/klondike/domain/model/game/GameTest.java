package com.patience.klondike.domain.model.game;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;
import com.patience.domain.model.event.DomainEvent;
import com.patience.domain.model.event.DomainEventPublisher;
import com.patience.domain.model.event.DomainEventSubscriber;

public class GameTest {

	private Game game;
		
	@Before
	public void setup() {
		GameId gameId = new GameId("d5a6b733-5ed5-4a9e-9af3-5cd18e7ec1cb");
		game = new Game(gameId);
		
		assertThat(game.gameId(), equalTo(gameId));
	}
	
	@Test
	public void gameInitialization() {			
		assertThat(24, equalTo(game.stock().cardCount()));
		assertThat(true, equalTo(game.waste().isEmpty()));
				
		List<Foundation> foundations = game.foundations();
		assertThat(4, equalTo(foundations.size()));
		assertThat(0, equalTo(foundations.get(0).cards().size()));
		assertThat(0, equalTo(foundations.get(1).cards().size()));
		assertThat(0, equalTo(foundations.get(2).cards().size()));
		assertThat(0, equalTo(foundations.get(3).cards().size()));
		
		List<TableauPile> tableauPiles = game.tableauPiles();
		
		assertThat(7, equalTo(tableauPiles.size()));
		assertThat(1, equalTo(tableauPiles.get(0).flippedCardCount()));
		assertThat(0, equalTo(tableauPiles.get(0).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(1).flippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(1).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(2).flippedCardCount()));
		assertThat(2, equalTo(tableauPiles.get(2).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(3).flippedCardCount()));
		assertThat(3, equalTo(tableauPiles.get(3).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(4).flippedCardCount()));
		assertThat(4, equalTo(tableauPiles.get(4).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(5).flippedCardCount()));
		assertThat(5, equalTo(tableauPiles.get(5).unflippedCardCount()));
		assertThat(1, equalTo(tableauPiles.get(6).flippedCardCount()));
		assertThat(6, equalTo(tableauPiles.get(6).unflippedCardCount()));
	}
	
	@Test
	public void drawCard() {
		game.drawCard();
		
		assertThat(23, equalTo(game.stock().cardCount()));
		assertThat(1, equalTo(game.waste().cardCount()));
	}
	
	@Test
	public void wasteRecyclesWhenStockIsEmpty() {
		for (int i = game.stock().cardCount(); i > 0; i--) {	
			game.drawCard();
		}
		
		assertThat(0, equalTo(game.stock().cardCount()));
		assertThat(24, equalTo(game.waste().cardCount()));
		
		game.drawCard();
		
		assertThat(24, equalTo(game.stock().cardCount()));
		assertThat(0, equalTo(game.waste().cardCount()));		
	}
	
	@Test
	public void stockRecycledEventFires() {
		final List<DomainEvent> events = newArrayList();
		
		DomainEventPublisher.instance().subscribe(new DomainEventSubscriber() {
			@Subscribe
			public void stockRecycled(DomainEvent event) {
				events.add(event);
			}
        });
		
		for (int i = game.stock().cardCount(); i >= 0; i--) {	
			game.drawCard();
		}
		
		assertThat(1, equalTo(events.size()));

		StockRecycled stockRecycled = (StockRecycled) events.get(0);
		assertThat(game.gameId(), equalTo(stockRecycled.gameId()));
	}
}
