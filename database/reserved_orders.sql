CREATE TABLE reserved_orders 
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    quantity   INT NOT NULL,
    PRIMARY KEY(order_id, product_id)
);