package com.patience.klondike.infrastructure.messaging;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;
import java.util.Set;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;

import com.jayway.jsonpath.JsonPath;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.application.representation.PlayingCardRepresentation;

public class GameScorerListener implements MessageListener {
	
	private Map<String, MessageHandler> delegates = newHashMap();
	
	private final GameScoreApplicationService gameScoreApplicationService;
	
	public GameScorerListener(GameScoreApplicationService gameScoreApplicationService) {		
		this.gameScoreApplicationService = checkNotNull(gameScoreApplicationService, "GameScoreApplicationService must be provided.");
		
		delegates.put("patience.klondike.GameCreated", new GameCreatedHandler());
		delegates.put("patience.klondike.CardFlipped", new CardFlippedHandler());
		delegates.put("patience.klondike.CardPromoted", new CardPromotedHandler());
		delegates.put("patience.klondike.CardsMovedToTableau", new CardsMovedToTableauHandler());
		delegates.put("patience.klondike.StockRecycled", new StockRecycledHandler());				
	}
	
	@Override
	public void onMessage(Message message) {
		MessageProperties messageProperties = message.getMessageProperties();
		
		String routingKey = messageProperties.getReceivedRoutingKey();
		// Possibly deduplicate with - String messageId = messageProperties.getMessageId();
		
		if (listensTo().contains(routingKey)) {
			String json = new String(message.getBody());
			delegates.get(routingKey).handle(json);
		}
	}
	
	private Set<String> listensTo() {
		return delegates.keySet();
	}
	
	class GameCreatedHandler implements MessageHandler {

		@Override
		public void handle(String message) {
			String gameId = JsonPath.read(message, "$.gameId.id");
			gameScoreApplicationService.createGameScore(gameId);	
		}
	}
	
	class CardFlippedHandler implements MessageHandler {

		@Override
		public void handle(String message) {
			String gameId = JsonPath.read(message, "$.gameId.id");
			String rank = JsonPath.read(message, "$.card.rank");
			String suit = JsonPath.read(message, "$.card.suit");
			
			PlayingCardRepresentation card = new PlayingCardRepresentation(rank, suit);
			
			gameScoreApplicationService.scoreCardFlip(gameId, card);		
		}		
	}
	
	class CardPromotedHandler implements MessageHandler {

		@Override
		public void handle(String message) {
			String gameId = JsonPath.read(message, "$.gameId.id");
			String rank = JsonPath.read(message, "$.card.rank");
			String suit = JsonPath.read(message, "$.card.suit");
			
			PlayingCardRepresentation card = new PlayingCardRepresentation(rank, suit);
			String origin = JsonPath.read(message, "$.origin");
			
			gameScoreApplicationService.scoreCardMove(gameId, card, origin, "Foundation");		
		}		
	}
	
	class CardsMovedToTableauHandler implements MessageHandler {

		@Override
		public void handle(String message) {
			String origin = JsonPath.read(message, "$.origin");
			
			if (origin.equals("Tableau")) {
				return;
			}
			
			String gameId = JsonPath.read(message, "$.gameId.id");
			String rank = JsonPath.read(message, "$.cards[0].rank");
			String suit = JsonPath.read(message, "$.cards[0].suit");
			
			PlayingCardRepresentation card = new PlayingCardRepresentation(rank, suit);
						
			gameScoreApplicationService.scoreCardMove(gameId, card, origin, "Tableau");		
		}		
	}
	
	class StockRecycledHandler implements MessageHandler {

		@Override
		public void handle(String message) {						
			String gameId = JsonPath.read(message, "$.gameId.id");							
			gameScoreApplicationService.scoreStockRecycle(gameId);		
		}		
	}	
}
