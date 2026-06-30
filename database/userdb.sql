CREATE DATABASE userdb;
USE userdb;

CREATE TABLE users
(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(300),
    email VARCHAR(300),
    password VARCHAR(300),
    keycloak_id VARCHAR(300)
);