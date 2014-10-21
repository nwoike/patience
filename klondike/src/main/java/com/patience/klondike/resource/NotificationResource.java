package com.patience.klondike.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.patience.domain.model.event.EventStore;
import com.patience.domain.model.event.StoredEvent;

@RestController
@RequestMapping("/notifications")
public class NotificationResource {

	// TODO: Replace with Notification Atom Service
	@Autowired
	private EventStore eventStore;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<StoredEvent>> unprocessedEvents() {		
	    List<StoredEvent> unprocessedEvents = eventStore.unprocessedEvents();
	    return new ResponseEntity<List<StoredEvent>>(unprocessedEvents, HttpStatus.OK);
	}
	
	@RequestMapping(value="/all", method = RequestMethod.GET)
	public ResponseEntity<List<StoredEvent>> allEvents() {		
	    List<StoredEvent> allEvents = eventStore.allEvents();
	    return new ResponseEntity<List<StoredEvent>>(allEvents, HttpStatus.OK);
	}
}
