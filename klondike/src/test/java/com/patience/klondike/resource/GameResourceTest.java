package com.patience.klondike.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.patience.klondike.application.GameApplicationService;
import com.patience.klondike.application.GameScoreApplicationService;
import com.patience.klondike.application.representation.GameRepresentation;
import com.patience.klondike.domain.model.game.DrawCount;
import com.patience.klondike.domain.model.game.Game;
import com.patience.klondike.domain.model.game.GameId;
import com.patience.klondike.domain.model.game.PassCount;
import com.patience.klondike.domain.model.game.Settings;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=GameResource.class, secure=false)
public class GameResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private GameApplicationService gameApplicationService;
	
	@MockBean
	private GameScoreApplicationService gameScoreService;
	
	@Test
	public void startGame() throws Exception {
        when(gameApplicationService.startGame("One", "Unlimited")).thenReturn("id");

        mockMvc.perform(post("/klondike").accept("application/json"))
	        .andExpect(status().isCreated())
	        .andExpect(header().string("Location", containsString("/klondike/id")));
	}

	@Test
	public void retrieveGame() throws Exception {
	    UUID id = UUID.randomUUID();
	    
        Game game = new Game(new GameId(id), new Settings(DrawCount.One, PassCount.Three));
        GameRepresentation representation = new GameRepresentation(game, false);
	    
        when(gameApplicationService.loadGame(id.toString())).thenReturn(representation);
        
		mockMvc.perform(get("/klondike/" + id.toString()).accept("application/json"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.gameId", equalTo(id.toString())))
	        .andDo(print());

	}
}
