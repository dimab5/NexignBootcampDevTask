CREATE TABLE IF NOT EXISTS tariffs (
    tariff_id SMALLINT PRIMARY KEY,
    tariff_rules JSONB NOT NULL
);

CREATE TABLE IF NOT EXISTS traffic (
    client_id VARCHAR(14) PRIMARY KEY,
    CONSTRAINT check_phone_number CHECK (client_id LIKE '79_________'),
    minutes_int_current_month BIGINT,
    minutes_ext_current_month BIGINT,
    month SMALLINT NOT NULL,
    tariff_id SMALLINT NOT NULL REFERENCES tariffs(tariff_id)
);

CREATE TABLE IF NOT EXISTS history (
    id BIGINT NOT NULL PRIMARY KEY,
    type VARCHAR(2) NOT NULL,
    client_id VARCHAR(14) NOT NULL,
    caller_id VARCHAR(14) NOT NULL,
    start_time BIGINT NOT NULL,
    end_time BIGINT NOT NULL,
    tariff_id SMALLINT NOT NULL REFERENCES tariffs(tariff_id),
    internal BOOLEAN NOT NULL,
    cost NUMERIC,
    duration SMALLINT NOT NULL
);

INSERT INTO tariffs
VALUES (11, '{
  "tariff_id": "11",
  "name": "Классика",
  "description": "Входящие - бесплатно. Исходящие внутри ромашки - 1,5 у.е/мин, для остальных - 2,5 у.е./мин",
  "currency": "RUB",
  "prepaid": null,
  "overlimit": {
    "internal_incoming": "0.00",
    "internal_outcoming": "1.50",
    "extermal_incoming": "0.00",
    "external_outcoming": "2.50"
  }
}'),
    (12, '{
      "tariff_id": "12",
      "name": "Помесячный",
      "description": "Лимит 50 минут на все звонки, сверх лимита - по тарифу Классика",
      "currency": "RUB",
      "prepaid": {
        "cost": "100",
        "limits": {
          "total_minutes": "50"
        }
      },
      "overlimit": {
        "reference_tariff_id": "11"
      }
  }')
