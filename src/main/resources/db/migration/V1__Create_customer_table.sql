CREATE TABLE customer
(
    uuid        CHAR(36)       NOT NULL PRIMARY KEY,
    first_name  VARCHAR(255)   NOT NULL,
    last_name   VARCHAR(255)   NOT NULL,
    balance_pln DECIMAL(10, 2) NOT NULL,
    balance_usd DECIMAL(10, 2) NOT NULL
);