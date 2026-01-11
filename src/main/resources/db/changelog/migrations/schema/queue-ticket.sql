CREATE TABLE queue_ticket (
                              id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                              ticket_number INT NOT NULL,
                              status VARCHAR(20) NOT NULL
);
