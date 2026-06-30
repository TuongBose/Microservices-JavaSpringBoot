CREATE DATABASE orderdb;
USE orderdb;

CREATE TABLE orders
(
	id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    product VARCHAR(300),
    price INT
);