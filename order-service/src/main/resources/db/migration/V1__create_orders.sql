CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        customer_name VARCHAR(100) NOT NULL,
                        restaurant_id BIGINT NOT NULL,
                        items TEXT NOT NULL, -- comma separated or JSON string for simplicity
                        total_price NUMERIC(10,2) NOT NULL,
                        status VARCHAR(50) NOT NULL DEFAULT 'PLACED',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);