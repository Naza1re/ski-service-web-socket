CREATE TABLE IF NOT EXISTS tariff (
                        id BIGSERIAL PRIMARY KEY,
                        code VARCHAR(255) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description TEXT NOT NULL,
                        start_time TIME NOT NULL,
                        hours INT NOT NULL
);