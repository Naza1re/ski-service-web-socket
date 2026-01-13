CREATE TABLE rental_order (
                              id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                              client_id BIGINT NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              status VARCHAR(20) NOT NULL, -- OPEN, CLOSED
                              CONSTRAINT fk_order_client
                                  FOREIGN KEY (client_id)
                                      REFERENCES client(id)
);
