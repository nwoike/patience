package com.patience.domain.model.event;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.newHashMap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

@Transactional(isolation=Isolation.SERIALIZABLE, propagation=Propagation.REQUIRED)
public class JdbcEventStore implements EventStore {

	private DomainEventSerializer eventSerializer;
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String appendEventSql = "INSERT INTO Events (event_type, event_body, occurred_on) VALUES(:eventType, :eventBody, :occurredOn)";
	
	private static final String unprocessedEventsSql = "SELECT event_id, event_type, event_body, occurred_on FROM Events WHERE event_id > (SELECT TOP 1 latest_event_id FROM ProcessedEventTracker) ORDER BY event_id";
	
	private static final String allEventsSql = "SELECT event_id, event_type, event_body, occurred_on FROM Events ORDER BY event_id";

	private static final String updateLastProcessedEventSql = "UPDATE ProcessedEventTracker SET latest_event_id = :eventId";
	
	public JdbcEventStore(DomainEventSerializer eventSerializer, NamedParameterJdbcTemplate jdbcTemplate) {
		this.eventSerializer = checkNotNull(eventSerializer, "Event Serializer must be provided.");		
		this.jdbcTemplate = checkNotNull(jdbcTemplate, "JDBC Template must be provided");
	}
	
	@Override
	public void append(DomainEvent domainEvent) {		
		String serializedEvent = eventSerializer.serialize(domainEvent);
		
		StoredEvent storedEvent = new StoredEvent(
			domainEvent.eventType(),
			domainEvent.occurredOn(),
			serializedEvent);
		
		Map<String, Object> parameters = newHashMap();
		parameters.put("eventBody", storedEvent.eventBody());
		parameters.put("eventType", storedEvent.eventType());
		parameters.put("occurredOn", storedEvent.occurredOn().toDate());
		
		jdbcTemplate.update(appendEventSql, parameters);
	}
	
	@Override
	public List<StoredEvent> allEvents() {		
		return jdbcTemplate.query(allEventsSql, new RowMapper<StoredEvent>() {
			@Override
			public StoredEvent mapRow(ResultSet resultSet, int index) throws SQLException {
				long eventId = resultSet.getLong("event_id");
				String eventType = resultSet.getString("event_type");
				DateTime occurredOn = new DateTime(resultSet.getTimestamp("occurred_on").getTime());
				String eventBody = resultSet.getString("event_body");
				
				return new StoredEvent(eventId, eventType, occurredOn, eventBody);
			}
		});
	}

	@Override
	public List<StoredEvent> unprocessedEvents() {		
		return jdbcTemplate.query(unprocessedEventsSql, new RowMapper<StoredEvent>() {
			@Override
			public StoredEvent mapRow(ResultSet resultSet, int index) throws SQLException {
				long eventId = resultSet.getLong("event_id");
				String eventType = resultSet.getString("event_type");
				DateTime occurredOn = new DateTime(resultSet.getTimestamp("occurred_on").getTime());
				String eventBody = resultSet.getString("event_body");
				
				return new StoredEvent(eventId, eventType, occurredOn, eventBody);
			}
		});
	}

	@Override
	public void updateLastProcessedEventId(long eventId) {
		Map<String, Object> parameters = Maps.newHashMap();
		parameters.put("eventId", eventId);
		
		jdbcTemplate.update(updateLastProcessedEventSql, parameters);		
	}
}