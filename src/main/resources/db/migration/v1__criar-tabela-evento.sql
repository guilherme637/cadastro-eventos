CREATE TABLE events (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    event_datetime TIMESTAMP NOT NULL,
    location VARCHAR(200)
);