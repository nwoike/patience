CREATE TABLE IF NOT EXISTS Klondike ( 
    game_id      VARCHAR(36)   PRIMARY KEY,
    data         TEXT          NOT NULL,
    version		 INTEGER       NOT NULL
);

CREATE TABLE IF NOT EXISTS KlondikeScore ( 
    game_id      VARCHAR(36)   PRIMARY KEY,
    data         TEXT          NOT NULL,
    version		 INTEGER       NOT NULL
);

CREATE TABLE IF NOT EXISTS Events (
	event_id 		INTEGER			GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
	event_type      VARCHAR(256)	NOT NULL,
	event_body		TEXT   		    NOT NULL,
	occurred_on		TIMESTAMP       NOT NULL
);

CREATE TABLE IF NOT EXISTS ProcessedEventTracker (
    constrain           INTEGER     PRIMARY KEY    NOT NULL   DEFAULT(1),
	latest_event_id		INTEGER 	NOT NULL 	   REFERENCES Events(event_id),
	CONSTRAINT pet_single_row CHECK (constrain = 1)
); 