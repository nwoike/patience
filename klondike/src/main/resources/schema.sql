CREATE TABLE IF NOT EXISTS Klondike ( 
    game_id      VARCHAR(32)   PRIMARY KEY,
    data         LONGVARCHAR   NOT NULL,
    version		 INTEGER       NOT NULL
);

CREATE TABLE IF NOT EXISTS Events (
	event_id 		INTEGER			GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	event_type      VARCHAR(256)	NOT NULL,
	event_body		LONGVARCHAR		NOT NULL,
	occurred_on		TIMESTAMP       NOT NULL
);

CREATE TABLE IF NOT EXISTS ProcessedEventTracker (
	latest_event_id		INTEGER 	NOT NULL 	REFERENCES Events(event_id)
);