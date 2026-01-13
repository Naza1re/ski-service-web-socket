CREATE TABLE equipment (
                           id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                           barcode VARCHAR(100) NOT NULL UNIQUE,
                           equipment_type_id BIGINT NOT NULL,
                           size VARCHAR(20),
                           status VARCHAR(20) NOT NULL, -- AVAILABLE, RENTED
                           CONSTRAINT fk_equipment_type
                               FOREIGN KEY (equipment_type_id)
                                   REFERENCES equipment_type(id)
);
