ALTER TABLE skipass
    DROP CONSTRAINT fk_skipass_client;

ALTER TABLE skipass
    ALTER COLUMN client_id DROP NOT NULL;

ALTER TABLE skipass
    ADD CONSTRAINT fk_skipass_client
        FOREIGN KEY (client_id)
            REFERENCES client(id)
            ON DELETE SET NULL;