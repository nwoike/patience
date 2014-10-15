package com.patience.klondike.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.application.representation.GameRepresentation;

@RestController
@RequestMapping("/klondike")
public class GameResource {

	private GameApplicationService service;
	
	public GameResource(GameApplicationService service) {
		this.service = checkNotNull(service, "Game Application Service must be provided.");
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<GameRepresentation> createGame(UriComponentsBuilder builder) {
		String gameId = service.startGame();

		UriComponents uriComponents = builder.path("/klondike/{id}").buildAndExpand(gameId);
		HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    
	    GameRepresentation gameRepresentation = service.retrieveGame(gameId);	
	    
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/{gameId}", method = RequestMethod.GET)
	public ResponseEntity<GameRepresentation> retrieveGame(@PathVariable String gameId) {
		GameRepresentation gameRepresentation = service.retrieveGame(gameId);	
	    return new ResponseEntity<GameRepresentation>(gameRepresentation, HttpStatus.OK);
	}
}