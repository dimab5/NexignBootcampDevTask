CREATE TABLE IF NOT EXISTS clients (
    client_id VARCHAR(14) PRIMARY KEY,
    CONSTRAINT check_phone_number CHECK (client_id LIKE '79_________'),
    name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS history (
    id SERIAL PRIMARY KEY,
    type VARCHAR(2) NOT NULL,
    client_id VARCHAR(14) NOT NULL,
    caller_id VARCHAR(14) NOT NULL,
    start_time BIGINT NOT NULL,
    end_time BIGINT NOT NULL
);

INSERT INTO clients
VALUES  ('79543457634', 'Dima'),
        ('79123456789', 'Anna'),
        ('79234567890', 'Alex'),
        ('79876543210', 'Kate'),
        ('79987654321', 'Max'),
        ('79876543211', 'Olga'),
        ('79123456788', 'Ivan'),
        ('79234567891', 'Maria'),
        ('79098765432', 'Sergey'),
        ('79512345678', 'Elena'),
        ('79876543212', 'Polina'),
        ('79123456787', 'Pavel'),
        ('79234567892', 'Natalia'),
        ('79098765431', 'Andrey'),
        ('79512345679', 'Svetlana'),
        ('79543457635', 'Anton'),
        ('79543457636', 'Elena'),
        ('79543457637', 'Maxim'),
        ('79543457638', 'Natalia'),
        ('79543457639', 'Sergei')