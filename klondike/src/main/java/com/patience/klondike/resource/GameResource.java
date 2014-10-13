package com.patience.klondike.resource;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.patience.klondike.application.GameApplicationService;

@Controller
@RequestMapping("/klondike")
public class GameResource {

	private GameApplicationService service;
	
	public GameResource(GameApplicationService service) {
		this.service = checkNotNull(service, "Game Application Service must be provided.");
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createGame(UriComponentsBuilder builder) {
		String gameId = service.startGame();

		UriComponents uriComponents = builder.path("/klondike/{id}").buildAndExpand(gameId);
		HttpHeaders headers = new HttpHeaders();
	    headers.setLocation(uriComponents.toUri());
	    
	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}