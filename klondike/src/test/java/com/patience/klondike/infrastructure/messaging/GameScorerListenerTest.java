package com.patience.klondike.infrastructure.messaging;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patience.common.domain.model.card.PlayingCard;
import com.patience.domain.model.event.DomainEventSerializer;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.application.representation.PlayingCardRepresentation;
import com.patience.klondike.domain.model.game.CardFlipped;
import com.patience.klondike.domain.model.game.CardPromoted;
import com.patience.klondike.domain.model.game.CardsMovedToTableau;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.StockRecycled;
import com.patience.klondike.domain.model.game.score.PileType;

public class GameScorerListenerTest {

	GameScorerListener scorerListener;
	
	GameScoreApplicationService mockScoreService;
	
	DomainEventSerializer domainEventSerializer = new DomainEventSerializer(new ObjectMapper());
	
	@Before
	public void setup() {
		mockScoreService = mock(GameScoreApplicationService.class);
		scorerListener = new GameScorerListener(mockScoreService);
	}
	
	@Test
	public void cardFlipped() {
		GameId gameId = new GameId(UUID.randomUUID());
		
		CardFlipped event = new CardFlipped(gameId, PlayingCard.AceOfClubs);
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setReceivedRoutingKey("patience.klondike.CardFlipped");
		
		Message message = new Message(domainEventSerializer.serialize(event).getBytes(), messageProperties);
		scorerListener.onMessage(message);
		
		ArgumentCaptor<PlayingCardRepresentation> captor = ArgumentCaptor.forClass(PlayingCardRepresentation.class);
		verify(mockScoreService).scoreCardFlip(eq(gameId.id()), captor.capture());
		
		PlayingCardRepresentation cardRepresentation = captor.getValue();
		assertThat("Ace", equalTo(cardRepresentation.getRank()));
		assertThat("Clubs", equalTo(cardRepresentation.getSuit()));
	}
		
	@Test
	public void cardPromoted() {		
		GameId gameId = new GameId(UUID.randomUUID());
		
		CardPromoted event = new CardPromoted(gameId, PlayingCard.AceOfClubs, PileType.Tableau);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setReceivedRoutingKey("patience.klondike.CardPromoted");
		
		Message message = new Message(domainEventSerializer.serialize(event).getBytes(), messageProperties);
		scorerListener.onMessage(message);
		
		ArgumentCaptor<PlayingCardRepresentation> captor = ArgumentCaptor.forClass(PlayingCardRepresentation.class);
		verify(mockScoreService).scoreCardMove(eq(gameId.id()), captor.capture(), eq("Tableau"), eq("Foundation"));
		
		PlayingCardRepresentation cardRepresentation = captor.getValue();
		assertThat("Ace", equalTo(cardRepresentation.getRank()));
		assertThat("Clubs", equalTo(cardRepresentation.getSuit()));
	}
	
	@Test
	public void cardsMovedToTableau() {
		GameId gameId = new GameId(UUID.randomUUID());
		
		CardsMovedToTableau event = new CardsMovedToTableau(gameId, newArrayList(PlayingCard.AceOfClubs), PileType.Waste);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setReceivedRoutingKey("patience.klondike.CardsMovedToTableau");
		
		Message message = new Message(domainEventSerializer.serialize(event).getBytes(), messageProperties);
		scorerListener.onMessage(message);
		
		ArgumentCaptor<PlayingCardRepresentation> captor = ArgumentCaptor.forClass(PlayingCardRepresentation.class);
		verify(mockScoreService).scoreCardMove(eq(gameId.id()), captor.capture(), eq("Waste"), eq("Tableau"));
		
		PlayingCardRepresentation cardRepresentation = captor.getValue();
		assertThat("Ace", equalTo(cardRepresentation.getRank()));
		assertThat("Clubs", equalTo(cardRepresentation.getSuit()));
	}

	@Test
	public void stockRecycled() {
		GameId gameId = new GameId(UUID.randomUUID());
		
		StockRecycled event = new StockRecycled(gameId);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setReceivedRoutingKey("patience.klondike.StockRecycled");
		
		Message message = new Message(domainEventSerializer.serialize(event).getBytes(), messageProperties);
		scorerListener.onMessage(message);
		
		verify(mockScoreService).scoreStockRecycle(gameId.id());
	}
}
