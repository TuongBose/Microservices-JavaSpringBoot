CREATE TABLE inventories (
    product_id INT NOT NULL PRIMARY KEY,
    quantity INT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);