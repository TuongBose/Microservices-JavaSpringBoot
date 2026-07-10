CREATE DATABASE orderdb;
USE orderdb;

CREATE TABLE orders
(
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    product VARCHAR(300),
    price DECIMAL(10,2),
    total DECIMAL(10,2),
    product_id INT,
    quantity INT,
    status  ENUM('PENDING','COMPLETED','CANCELLED')
);