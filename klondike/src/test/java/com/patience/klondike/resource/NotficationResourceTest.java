package com.patience.klondike.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import com.patience.domain.model.event.EventStore;
import com.patience.klondike.TestConfig;
import com.patience.klondike.infrastructure.application.Application;
import com.patience.klondike.infrastructure.persistence.InMemoryGameRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class, TestConfig.class}, loader = SpringApplicationContextLoader.class)
public class NotficationResourceTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private InMemoryGameRepository gameRepository;
	
	@Autowired
	private EventStore eventStore;
	
	@Before
	public void setup() {
		this.mockMvc = webAppContextSetup(this.wac).build();
		this.gameRepository.reset();
	}

	@Test
	public void retrieveNotifications() throws Exception {
		mockMvc.perform(post("/klondike").accept("application/json"))
	        .andExpect(status().isCreated())
	        .andReturn();
	
		MvcResult retrieveResult = mockMvc.perform(get("/notifications").accept("application/json"))
	        .andExpect(status().isOk())
	        .andReturn();

		System.out.println(retrieveResult.getResponse().getContentAsString());
		
		Thread.sleep(500);
	}
}