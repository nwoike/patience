package com.patience.klondike.solver;

import static com.google.common.collect.Lists.newArrayList;
import static com.patience.common.domain.model.card.PlayingCard.AceOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.AceOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.AceOfHearts;
import static com.patience.common.domain.model.card.PlayingCard.EightOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.EightOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.EightOfSpades;
import static com.patience.common.domain.model.card.PlayingCard.FiveOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.FiveOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.FourOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.FourOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.JackOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.JackOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.JackOfHearts;
import static com.patience.common.domain.model.card.PlayingCard.JackOfSpades;
import static com.patience.common.domain.model.card.PlayingCard.KingOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.NineOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.NineOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.NineOfHearts;
import static com.patience.common.domain.model.card.PlayingCard.SevenOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.SevenOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.SixOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.SixOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.TenOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.ThreeOfClubs;
import static com.patience.common.domain.model.card.PlayingCard.ThreeOfDiamonds;
import static com.patience.common.domain.model.card.PlayingCard.ThreeOfHearts;
import static com.patience.common.domain.model.card.PlayingCard.TwoOfClubs;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.cardstack.CardStack;

public class GameAnalyzerTest {
	
	@Test
	public void promotesAceFromWaste() {		
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(JackOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), AceOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new PromoteCard(AceOfClubs);
		assertThat(availableMoves, contains(move));
	}

	@Test
	public void promotesTwoFromWasteWhenAceIsInFoundation() {
		List<PlayingCard> foundations = newArrayList(
			AceOfClubs
		);
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs),
			cardStack(NineOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), TwoOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
				
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new PromoteCard(TwoOfClubs);
		assertThat(availableMoves, contains(move));
	}		
		
	@Test
	public void promotesTwoFromTableauWhenAceIsInFoundation() {
		List<PlayingCard> foundations = newArrayList(
			AceOfClubs
		);
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),   // Promote
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(JackOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), EightOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new PromoteCard(TwoOfClubs);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void promotesFromWasteAndTableauWhenNextInFoundation() {		
		PlayingCard waste = PlayingCard.SixOfClubs; // Promote
		
		List<PlayingCard> foundations = newArrayList(
			FiveOfClubs,
			SevenOfDiamonds
		);
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(EightOfDiamonds),	// Promote
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(JackOfSpades),
			cardStack(JackOfDiamonds),
			cardStack(JackOfHearts),
			cardStack(JackOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), waste, true); 
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(2));
		
		Move move = new PromoteCard(EightOfDiamonds);
		Move otherMove = new PromoteCard(SixOfClubs);
		assertThat(availableMoves, containsInAnyOrder(move, otherMove));
	}
	
	@Test
	public void promoteAceFromTableauPile() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfClubs),
			cardStack(AceOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(JackOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), TwoOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new PromoteCard(AceOfClubs);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void multipleAcePromotions() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(AceOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(AceOfHearts),
			cardStack(SevenOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), AceOfDiamonds, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(3));
			
		Move move = new PromoteCard(AceOfClubs);
		Move otherMove = new PromoteCard(AceOfHearts);
		Move anotherMove = new PromoteCard(AceOfDiamonds);
		assertThat(availableMoves, containsInAnyOrder(move, otherMove, anotherMove));		
	}
	
	@Test
	public void flipAvailable() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles, 1), NineOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new FlipCard(2);
		assertThat(availableMoves, contains(move));
	}
		
	@Test
	public void moveFromWasteToTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(NineOfHearts)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), EightOfSpades, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new MoveCard(PileType.Waste, EightOfSpades, 7);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void moveSingleCardFromTableauToTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfDiamonds),
			cardStack(FourOfDiamonds),
			cardStack(FiveOfDiamonds),
			cardStack(SixOfDiamonds),
			cardStack(SevenOfDiamonds),
			cardStack(EightOfDiamonds)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), NineOfDiamonds, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new MoveCard(PileType.TableauPile, TwoOfClubs, 2);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void moveSingleCardFromTableauToTableauMultipleOptions() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfDiamonds),
			cardStack(ThreeOfHearts),
			cardStack(FiveOfDiamonds),
			cardStack(SixOfDiamonds),
			cardStack(SevenOfDiamonds),
			cardStack(EightOfDiamonds)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), NineOfDiamonds, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(2));
		
		Move move = new MoveCard(PileType.TableauPile, TwoOfClubs, 2);
		Move otherMove = new MoveCard(PileType.TableauPile, TwoOfClubs, 3);
		
		assertThat(availableMoves, containsInAnyOrder(move, otherMove));
	}
	
	@Test
	public void moveMultipleSingleCardsFromTableauToTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfDiamonds),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), NineOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(2));
		
		Move move = new MoveCard(PileType.TableauPile, TwoOfClubs, 2);
		Move otherMove = new MoveCard(PileType.TableauPile, ThreeOfDiamonds, 3);

		assertThat(availableMoves, containsInAnyOrder(move, otherMove));
	}
	
	@Test
	public void moveMultipleCardsFromTableauToTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(ThreeOfDiamonds, TwoOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs),
			cardStack(NineOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), TenOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new MoveCard(PileType.TableauPile, ThreeOfDiamonds, 2);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void moveKingFromWasteToEmptyTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfClubs),
			cardStack(),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), KingOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new MoveCard(PileType.Waste, KingOfClubs, 3);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void cannotMoveKingFromWasteToTableauWithUnflippedCard() {
		List<PlayingCard> foundations = newArrayList();
		
		List<TableauPileData> tableauPiles = newArrayList(
			new TableauPileData(cardStack(TwoOfClubs), 1),
			new TableauPileData(cardStack(ThreeOfClubs), 1),
			new TableauPileData(cardStack(), 1),
			new TableauPileData(cardStack(FiveOfClubs), 1),
			new TableauPileData(cardStack(SixOfClubs), 1),
			new TableauPileData(cardStack(SevenOfClubs), 1),
			new TableauPileData(cardStack(EightOfClubs), 1)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles, KingOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();	
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new FlipCard(3);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void cannotMoveKingFromTableauToTableauWithUnflippedCard() {
		List<PlayingCard> foundations = newArrayList();
		
		List<TableauPileData> tableauPiles = newArrayList(
			new TableauPileData(cardStack(TwoOfClubs), 1),
			new TableauPileData(cardStack(ThreeOfClubs), 1),
			new TableauPileData(cardStack(), 1),
			new TableauPileData(cardStack(FiveOfClubs), 1),
			new TableauPileData(cardStack(SixOfClubs), 1),
			new TableauPileData(cardStack(SevenOfClubs), 1),
			new TableauPileData(cardStack(KingOfClubs), 1)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles, NineOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new FlipCard(3);
		assertThat(availableMoves, contains(move));
	}
	
	@Test
	public void moveKingFromTableauToTableau() {
		List<PlayingCard> foundations = newArrayList();
		
		List<TableauPileData> tableauPiles = newArrayList(
			new TableauPileData(cardStack(TwoOfClubs), 1),
			new TableauPileData(cardStack(ThreeOfClubs), 1),
			new TableauPileData(cardStack(), 0),
			new TableauPileData(cardStack(FiveOfClubs), 1),
			new TableauPileData(cardStack(SixOfClubs), 1),
			new TableauPileData(cardStack(SevenOfClubs), 1),
			new TableauPileData(cardStack(KingOfClubs), 2)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles, EightOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
				
		Move move = new MoveCard(PileType.TableauPile, KingOfClubs, 3);
		assertThat(availableMoves, contains(move));
	}
		
	@Test
	public void doNotMoveKingBetweenLanes() {
		List<PlayingCard> foundations = newArrayList();
		
		List<TableauPileData> tableauPiles = newArrayList(
			new TableauPileData(cardStack(TwoOfClubs), 1),
			new TableauPileData(cardStack(ThreeOfClubs), 1),
			new TableauPileData(cardStack(), 0),
			new TableauPileData(cardStack(FiveOfClubs), 1),
			new TableauPileData(cardStack(SixOfClubs), 1),
			new TableauPileData(cardStack(SevenOfClubs), 1),
			new TableauPileData(cardStack(KingOfClubs), 0)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles, EightOfClubs, true);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();	
		
		assertThat(availableMoves.size(), equalTo(0));
	}
	
	@Test
	public void drawCard() {
		List<PlayingCard> foundations = newArrayList();
		
		List<CardStack> tableauPiles = newArrayList(
			cardStack(TwoOfClubs),
			cardStack(ThreeOfClubs),
			cardStack(FourOfClubs),
			cardStack(FiveOfClubs),
			cardStack(SixOfClubs),
			cardStack(SevenOfClubs),
			cardStack(EightOfClubs)
		);
		
		KlondikeGameData klondikeData = new KlondikeGameData(foundations, tableauPiles(tableauPiles), NineOfClubs);
		
		GameAnalyzer analyzer = new GameAnalyzer(klondikeData);
		List<Move> availableMoves = analyzer.availableMoves();
		
		assertThat(availableMoves.size(), equalTo(1));
		
		Move move = new DrawCard();
		assertThat(availableMoves, contains(move));
	}	
		
	CardStack cardStack(PlayingCard... cards) {
		return new CardStack(asList(cards));
	}
	
	List<TableauPileData> tableauPiles(List<CardStack> cardStacks) {
		return tableauPiles(cardStacks, 0);
	}
	
	List<TableauPileData> tableauPiles(List<CardStack> cardStacks, int unflippedCount) {
		List<TableauPileData> tableauPileData = newArrayList();
		
		for (CardStack cardStack : cardStacks) {
			tableauPileData.add(new TableauPileData(cardStack, unflippedCount));
		}		
		
		return tableauPileData;
	}
}
