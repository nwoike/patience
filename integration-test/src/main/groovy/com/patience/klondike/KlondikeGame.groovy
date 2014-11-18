package com.patience.klondike;

import com.patience.common.domain.model.card.PlayingCard;
import com.patience.common.domain.model.card.Rank;
import com.patience.common.domain.model.card.Suit;
import com.patience.common.domain.model.cardstack.CardStack
import com.patience.klondike.solver.KlondikeGameData
import com.patience.klondike.solver.NaiveKlondikeAI
import com.patience.klondike.solver.Move;
import com.patience.klondike.solver.TableauPileData

import geb.Page;

public class KlondikeGame extends Page {

	 static url = "#/klondike"
	 static at = { waitFor { $("es-card").size() > 0 } }		
	  
	 static content = {
		 stock(wait: true) { $("es-stock") }
		 waste(wait: true) { $("es-waste") }
		 tableauPiles(wait: true) { $("es-tableau-pile") }
		 foundations(wait: true) { $("es-foundation") }	 
	 }
	 
	 def drawCard() {
		 int wasteCardCount = $('es-waste es-card').size()		 
		 stock.click()
		 
		 waitFor(5) {
			 $('es-waste es-card').size() != wasteCardCount
		 }
	 }
	 
	 def wasteEmpty() {
		waitFor(15) {
			$('es-waste es-card').size() == 0
		}
	 }
	 
	 def foundationFull() {
		 waitFor(15) {
			 $('es-foundation es-card').size() == 52
		 }
	 }
	 
	 def flipCard(tableauPileId) {
		 tableauPiles[tableauPileId-1].find("es-card-back:last-child").click()
		 
		 waitFor(5) {
			 $("es-tableau-pile:nth-child($tableauPileId) es-card:last-child")
		 }
	 }
	 
	 def moveCards(rank, suit, tableauPileId) {
		 def dragTarget = cardOf(rank, suit)
		 def tableauPile = tableauPiles[tableauPileId-1]
		 int tableauPileCardCount = tableauPile.find("es-card").size()
		 def dropTarget = tableauPile.find("es-card:last-child") ?: tableauPile
		 		 
		 interact {
			 moveToElement(dragTarget, 0, 0)
			 clickAndHold()
			 moveToElement(dropTarget)
			 release()
		 }
		 
		 waitFor(5) {
			 tableauPile.find("es-card").size() > tableauPileCardCount
		 }
	 }
	 
	 def promoteCard(rank, suit) {
		 int foundationCardCount = $('es-foundation es-card').size()
		 
		 interact {			 
			 doubleClick(cardOf(rank, suit))
		 }
		 
		 waitFor(5) {
			 $('es-foundation es-card').size() > foundationCardCount 
		 }
	 }
	 
	 def cardOf(rank, suit) {
		 waitFor(5) {
			 $("es-card[rank=$rank][suit=$suit]")
		 }
		 
		 $("es-card[rank=$rank][suit=$suit]")
	 }
	 
	 def isWon() {
	 	$("es-tableau-pile es-card").size() + $("es-foundation es-card").size() == 52
	 }
	 
	 def gameData() {
		 if (isWon()) return null;
		 
		 def foundationCards = []
		 
		 $('es-foundation es-card:last-child').each { card ->
			 foundationCards.add(cardFromEl(card));
		 }
		 
		 def tableauPileData = []
		 
		 $('es-tableau-pile').eachWithIndex { el, idx ->
			 def unflippedCardCount = el.find('es-card-back').size()
			 def cards = []
			 
			 el.find('es-card').each { card ->
				 cards.add(cardFromEl(card))
			 }
 
			 tableauPileData.add(new TableauPileData(new CardStack(cards), unflippedCardCount))
		 }
					 
		 def wasteEl = $('es-waste es-card:last-child')
		 def wasteCard = !wasteEl ? null : cardFromEl(wasteEl)
		 
		 return new KlondikeGameData(foundationCards, tableauPileData, wasteCard)
	 }
	 
	 def cardFromEl(el) {		 
		 PlayingCard.of(Rank.valueOf(el.@rank), Suit.valueOf(el.@suit))
	 }
}
