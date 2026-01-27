CREATE TABLE IF NOT EXISTS cell (
                      id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                      cell_number VARCHAR(50) NOT NULL UNIQUE,
                      status VARCHAR(50) NOT NULL
);


ALTER TABLE rental_order
    ADD COLUMN cell_id BIGINT;


ALTER TABLE rental_order
    ADD CONSTRAINT fk_rental_order_cell
        FOREIGN KEY (cell_id) REFERENCES cell(id);