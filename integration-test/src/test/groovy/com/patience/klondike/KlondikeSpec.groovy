package com.patience.klondike

import com.patience.common.domain.model.card.PlayingCard;
import geb.Browser
import geb.spock.GebSpec

import com.patience.common.domain.model.card.PlayingCard
import com.patience.klondike.solver.KlondikeAI;
import com.patience.klondike.solver.Move
import com.patience.klondike.solver.MoveHandler
import com.patience.klondike.solver.NaiveKlondikeAI

class KlondikeSpec extends GebSpec {
	
	KlondikeAI solver = new NaiveKlondikeAI()
	
	def "Can play a game of Klondike Solitaire"() {
		when:
		to KlondikeGame, "b5d2fa8c-fe49-4628-8e32-611a524d7523"
	
		then:
		at KlondikeGame	
		
		Move move = solver.bestAvailableMove(gameData())
			
		while (!isWon() && move) {
			move.play(new MoveHandler() {
				void drawCard() {
					page.drawCard()
				}
				
				void flipCard(int tableauPileId) {
					page.flipCard(tableauPileId)
				}
				
				void moveCards(PlayingCard card, int tableauPileId) {
					page.moveCards(card.rank(), card.suit(), tableauPileId)
				}
				
				void promoteCard(PlayingCard card) {
					page.promoteCard(card.rank(), card.suit())
				}
			})
		
			move = solver.bestAvailableMove(gameData())
		}
		
		then:
		foundationFull()

		and:
		wasteEmpty()
	}	
}