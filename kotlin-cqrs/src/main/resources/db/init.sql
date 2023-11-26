CREATE TABLE IF NOT EXISTS event_store (
    id UUID,
    version INT,
    aggregate_type VARCHAR(255),
    event_type VARCHAR(255),
    payload VARCHAR(255),
    timestamp TIMESTAMP,
    PRIMARY KEY (id, version)
);