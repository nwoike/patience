package com.patience.klondike.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.application.IllegalMoveException;
import com.patience.klondike.application.representation.GameRepresentation;
import com.patience.klondike.application.representation.GameScoreRepresentation;
import com.patience.klondike.application.representation.PlayingCardRepresentation;
import com.patience.klondike.resource.request.CreateGame;
import com.patience.klondike.resource.request.FlipCard;
import com.patience.klondike.resource.request.MoveCards;
import com.patience.klondike.resource.request.PromoteCard;

@RestController
@RequestMapping("/klondike")
public class GameResource {

	private GameApplicationService service;
	
	private GameScoreApplicationService scoreService;
	
	public GameResource(GameApplicationService service, GameScoreApplicationService scoreService) {
		this.service = checkNotNull(service, "GameApplicationService must be provided.");
		this.scoreService = checkNotNull(scoreService, "GameScoreApplicationService must be provided.");
	}
	
	@ExceptionHandler(IllegalMoveException.class)	
	public ResponseEntity<ErrorMessage> handleError(IllegalMoveException exception) {		
		return new ResponseEntity<ErrorMessage>(new ErrorMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> createGame(@RequestBody(required=false) CreateGame createGame, UriComponentsBuilder builder) {
		if (createGame == null) {
			createGame = new CreateGame();
		}
		
		String gameId = service.startGame(createGame.getDrawCount(), createGame.getPassCount());

		UriComponents uriComponents = builder.path("/klondike/{id}").buildAndExpand(gameId);
		HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    
	    GameRepresentation gameRepresentation = service.loadGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{gameId}", method = RequestMethod.GET)
	public ResponseEntity<GameRepresentation> retrieveGame(@PathVariable String gameId) {
		GameRepresentation gameRepresentation = service.loadGame(gameId);
		
		if (gameRepresentation == null) {
			return new ResponseEntity<GameRepresentation>(HttpStatus.NOT_FOUND);
			
		} else {
			return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
		}
	}		
	
	@RequestMapping(value="/{gameId}/score", method = RequestMethod.GET)
	public ResponseEntity<GameScoreRepresentation> retrieveGameScore(@PathVariable String gameId) {
		long score = scoreService.retrieveGameScore(gameId);
		
		GameScoreRepresentation scoreRepresentation = new GameScoreRepresentation(score);
		
		return new ResponseEntity<GameScoreRepresentation>(scoreRepresentation, HttpStatus.OK);		
	}	
	
	@RequestMapping(value="/{gameId}/draw", method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> drawCard(@PathVariable String gameId) {
		service.drawCards(gameId);	
		
		GameRepresentation gameRepresentation = service.loadGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{gameId}/flip", method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> flipCard(@PathVariable String gameId, @RequestBody FlipCard request) {
		service.flipCard(gameId, request.getTableauPileId());	
		
		GameRepresentation gameRepresentation = service.loadGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
	}
		
	@RequestMapping(value="/{gameId}/move", method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> moveCards(@PathVariable String gameId, @RequestBody MoveCards request) {
		service.moveCards(gameId, request.getCards(), request.getTableauPileId());
		
		GameRepresentation gameRepresentation = service.loadGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{gameId}/promote", method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> promoteCard(@PathVariable String gameId, @RequestBody PromoteCard request) {
		PlayingCardRepresentation card = new PlayingCardRepresentation(request.getRank(), request.getSuit());
		int foundationId = request.getFoundationId();
		
		service.promoteCard(gameId, card, foundationId);	
		
		GameRepresentation gameRepresentation = service.loadGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
	}
}