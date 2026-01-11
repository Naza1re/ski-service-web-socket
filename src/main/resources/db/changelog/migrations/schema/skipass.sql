CREATE TABLE skipass (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         barcode VARCHAR(100) NOT NULL UNIQUE,
                         client_id BIGINT NOT NULL,
                         valid_from TIMESTAMP NOT NULL,
                         valid_to TIMESTAMP NOT NULL,
                         CONSTRAINT fk_skipass_client
                             FOREIGN KEY (client_id)
                                 REFERENCES client(id)
);
