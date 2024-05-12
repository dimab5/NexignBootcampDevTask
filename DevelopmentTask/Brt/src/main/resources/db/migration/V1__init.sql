CREATE TABLE IF NOT EXISTS clients (
    client_id VARCHAR(14) PRIMARY KEY,
    CONSTRAINT check_phone_number CHECK (client_id LIKE '79_________'),
    name VARCHAR(20),
    tariff_id SMALLINT NOT NULL,
    balance NUMERIC
);

CREATE TABLE IF NOT EXISTS history (
    id SERIAL PRIMARY KEY,
    type VARCHAR(2) NOT NULL,
    client_id VARCHAR(14) NOT NULL REFERENCES clients(client_id),
    caller_id VARCHAR(14) NOT NULL,
    start_time BIGINT NOT NULL,
    end_time BIGINT NOT NULL,
    tariff_id SMALLINT,
    internal BOOLEAN,
    cost NUMERIC,
    hrs_status VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS tariff_payments_history (
    id SERIAL PRIMARY KEY,
    client_id VARCHAR(14) NOT NULL REFERENCES clients(client_id),
    tariff_id SMALLINT NOT NULL,
    cost NUMERIC NOT NULL,
    time BIGINT NOT NULL
);

INSERT INTO clients
VALUES  ('79543457634', 'Dima', 12, 1000),
        ('79123456789', 'Anna', 11, 1000),
        ('79234567890', 'Alex', 12, 1000),
        ('79876543210', 'Kate', 11, 1000),
        ('79987654321', 'Max', 12, 1000),
        ('79876543211', 'Olga', 11, 1000),
        ('79123456788', 'Ivan', 11, 1000),
        ('79234567891', 'Maria', 11, 1000),
        ('79098765432', 'Sergey', 11, 1000),
        ('79512345678', 'Elena', 11, 1000),
        ('79876543212', 'Polina', 11, 1000)
--         ('79123456787', 'Pavel', 11, 1000),
--         ('79234567892', 'Natalia', 12, 1000),
--         ('79098765431', 'Andrey', 11, 1000),
--         ('79512345679', 'Svetlana', 11, 1000),
--         ('79543457635', 'Anton', 11, 1000),
--         ('79543457636', 'Elena', 11, 1000),
--         ('79543457637', 'Maxim', 11, 1000),
--         ('79543457638', 'Natalia', 11, 1000),
--         ('79543457639', 'Sergei', 11, 1000)