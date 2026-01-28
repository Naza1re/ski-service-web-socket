CREATE TABLE IF NOT EXISTS tariff_equipment_type_matrix (
                                              id BIGSERIAL PRIMARY KEY,
                                              tariff_id BIGINT NOT NULL REFERENCES tariff(id) ON DELETE CASCADE,
                                              equipment_type_id BIGINT NOT NULL REFERENCES equipment_type(id) ON DELETE CASCADE,
                                              price_per_hour NUMERIC(19, 2) NOT NULL
);