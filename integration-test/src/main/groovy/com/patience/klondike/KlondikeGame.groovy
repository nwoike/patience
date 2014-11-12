package com.patience.klondike;

import geb.Page;

public class KlondikeGame extends Page {

	 static url = "http://localhost:8080/#/klondike/b5d2fa8c-fe49-4628-8e32-611a524d7523"
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
}
