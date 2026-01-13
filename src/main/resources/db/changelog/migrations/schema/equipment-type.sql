CREATE TABLE equipment_type (
                                id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                name VARCHAR(50) NOT NULL -- лыжи, сноуборд, шлем и т.д.
);
