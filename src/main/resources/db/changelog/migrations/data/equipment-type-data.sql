INSERT INTO equipment_type (code, name)
VALUES
    ('SKI', 'Лыжи'),
    ('HELMET', 'Шлем'),
    ('SNOWBOARD','Сноуборд')
ON CONFLICT (code)
    DO UPDATE SET
    name = EXCLUDED.name;
