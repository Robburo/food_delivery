CREATE TABLE payments
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT         NOT NULL, -- from order-service
    amount     NUMERIC(10, 2) NOT NULL,
    status     VARCHAR(50)    NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);