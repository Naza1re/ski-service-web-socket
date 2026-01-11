CREATE TABLE client (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        full_name VARCHAR(255) NOT NULL,
                        height INT,
                        weight INT,
                        shoe_size INT,
                        skill_level VARCHAR(20),
                        queue_ticket_id BIGINT UNIQUE,
                        CONSTRAINT fk_client_queue
                            FOREIGN KEY (queue_ticket_id)
                                REFERENCES queue_ticket(id)
);
