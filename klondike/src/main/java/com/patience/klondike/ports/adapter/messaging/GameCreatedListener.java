package com.patience.klondike.ports.adapter.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.jayway.jsonpath.JsonPath;

public class GameCreatedListener implements MessageListener {
		
	@Override
	public void onMessage(Message message) {
		String json = new String(message.getBody());
		System.out.println(json);
		String gameId = JsonPath.read(json, "$.gameId.id");
	
		System.out.println("!!! Game Created: " + gameId);
	}
}
