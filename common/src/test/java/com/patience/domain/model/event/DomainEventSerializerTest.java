package com.patience.domain.model.event;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.junit.After;
import org.junit.Test;

import com.jayway.jsonpath.JsonPath;

public class DomainEventSerializerTest {

	static class SampleEvent implements DomainEvent {

		private String foo;
		
		private DateTime occurredOn;
		
		private String eventType = "sample.event";
		
		private int eventVersion = 1;
		
		public SampleEvent(String foo) {
			this.foo = foo;
			this.occurredOn = DateTime.now();
		}
		
		public String foo() {
			return foo;
		}
		
		@Override
		public int eventVersion() {
			return eventVersion;
		}

		@Override
		public String eventType() {
			return eventType;
		}

		@Override
		public DateTime occurredOn() {			
			return occurredOn;
		}
		
	}
	
	@Test
	public void serialize() {
		DateTimeUtils.setCurrentMillisFixed(System.currentTimeMillis());
	
		DomainEventSerializer serializer = new DomainEventSerializer();
		String json = serializer.serialize(new SampleEvent("bar"));
		
		String foo = JsonPath.read(json, "$.foo");
		Integer eventVersion = JsonPath.read(json, "$.eventVersion");
		String eventType = JsonPath.read(json, "$.eventType");
		Long occurredOn = JsonPath.read(json, "$.occurredOn");
		
		assertThat(foo, equalTo("bar"));
		assertThat(eventVersion, equalTo(1));
		assertThat(eventType, equalTo("sample.event"));
		assertThat(occurredOn, equalTo(DateTime.now().getMillis()));
	}

	@After
	public void tearDown() {
		DateTimeUtils.setCurrentMillisSystem();
	}
}
