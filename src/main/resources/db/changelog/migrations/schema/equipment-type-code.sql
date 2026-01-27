ALTER TABLE equipment_type
    ADD COLUMN code VARCHAR(50);

ALTER TABLE equipment_type
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE equipment_type
    ADD CONSTRAINT uk_equipment_type_code UNIQUE (code);
