CREATE TABLE rental_order_item (
                                   id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                   rental_order_id BIGINT NOT NULL,
                                   equipment_id BIGINT NOT NULL,
                                   CONSTRAINT fk_item_order
                                       FOREIGN KEY (rental_order_id)
                                           REFERENCES rental_order(id),
                                   CONSTRAINT fk_item_equipment
                                       FOREIGN KEY (equipment_id)
                                           REFERENCES equipment(id)
);
