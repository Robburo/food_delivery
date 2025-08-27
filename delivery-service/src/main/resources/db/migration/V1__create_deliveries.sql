CREATE TABLE deliveries
(
    id          BIGSERIAL PRIMARY KEY,
    order_id    BIGINT      NOT NULL, -- reference to the original order
    driver_name VARCHAR(100),         -- can be null until assigned
    status      VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
);