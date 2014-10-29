INSERT INTO Events (event_type, event_body, occurred_on) VALUES ('startup', '{}', CURRENT_TIMESTAMP);
INSERT INTO ProcessedEventTracker VALUES(SELECT TOP 1 event_id FROM Events);