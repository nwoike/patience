package com.patience.klondike.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.StoredEvent;

@RestController
@RequestMapping("/notifications")
public class NotificationResource {

	private EventStore eventStore;
	
	public NotificationResource(EventStore eventStore) {
        this.eventStore = eventStore;        
    }
	
	@GetMapping
	public ResponseEntity<List<StoredEvent>> unprocessedEvents() {		
	    List<StoredEvent> unprocessedEvents = eventStore.unprocessedEvents();
	    return new ResponseEntity<List<StoredEvent>>(unprocessedEvents, HttpStatus.OK);
	}
	
	@GetMapping(value="/all")
	public ResponseEntity<List<StoredEvent>> allEvents() {		
	    List<StoredEvent> allEvents = eventStore.allEvents();
	    return new ResponseEntity<List<StoredEvent>>(allEvents, HttpStatus.OK);
	}
}
