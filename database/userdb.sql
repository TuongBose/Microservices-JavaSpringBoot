CREATE DATABASE USERDB;
USE USERDB;

CREATE TABLE USERS
(
	ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(300),
    EMAIL VARCHAR(300)
);