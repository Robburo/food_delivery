CREATE TABLE restaurant_orders
(
    id            BIGSERIAL PRIMARY KEY,
    order_id      BIGINT         NOT NULL, -- from order-service
    customer_name VARCHAR(100)   NOT NULL,
    items         TEXT           NOT NULL,
    total_price   NUMERIC(10, 2) NOT NULL,
    status        VARCHAR(50)    NOT NULL DEFAULT 'PLACED',
    created_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP
);