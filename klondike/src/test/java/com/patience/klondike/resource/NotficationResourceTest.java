package com.patience.klondike.resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.StoredEvent;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers=NotificationResource.class, secure=false)
public class NotficationResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EventStore eventStore;

	@Test
	public void retrieveNotifications() throws Exception {
	    List<StoredEvent> events = new ArrayList<>();
	    events.add(new StoredEvent("com.klondike.test-event", DateTime.now(), "{\"foo\":\"bar\"}"));
	    
        when(eventStore.unprocessedEvents()).thenReturn(events);
	    	
		mockMvc.perform(get("/notifications").accept("application/json"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$[0].eventType", equalTo("com.klondike.test-event")))
	        .andExpect(jsonPath("$[0].eventBody", equalTo("{\"foo\":\"bar\"}")));
	}
}
