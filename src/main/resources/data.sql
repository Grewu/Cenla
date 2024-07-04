create database bank;

create schema if not exists bank;

CREATE TABLE person
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255)   NOT NULL,
    password VARCHAR(255)   NOT NULL,
    email    VARCHAR(255)   NOT NULL,
    cash     NUMERIC(10, 2) NOT NULL
);

CREATE TABLE bank
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE account
(
    id             SERIAL PRIMARY KEY,
    user_id        INT          NOT NULL,
    bank_id        INT          NOT NULL,
    balance        NUMERIC(15, 2) DEFAULT 0,
    account_number VARCHAR(20)  NOT NULL,
    name_user      VARCHAR(100) NOT NULL,
    opening_time   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    closing_time   TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES person (id),
    FOREIGN KEY (bank_id) REFERENCES bank (id)
);

ALTER TABLE account
    ADD CONSTRAINT unique_account_number UNIQUE (account_number);

CREATE TABLE transactions
(
    id                      SERIAL PRIMARY KEY,
    sender_account_number   VARCHAR(50) REFERENCES account (account_number),
    receiver_account_number VARCHAR(50) REFERENCES account (account_number),
    transaction_amount      NUMERIC(15, 2) NOT NULL,
    transaction_timestamp   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    transaction_type        VARCHAR(50)    NOT NULL
);


-- Таблица person
INSERT INTO person (username, password, email, cash)
VALUES ('user1', '$2a$10$E7.U0j9kJQf9pFTCSKA9KOJt/jbvmE6a6kIG3e2ZjO4Rf8QZusCZi', 'user1@example.com', 1000.00),
       ('user2', '$2a$10$9RRBTMlmOzkJ1CmBlT0tyusyWIr/AzC6wXPTDxG1x7feZ0o8bcP8K', 'user2@example.com', 500.00),
       ('user3', '$2a$10$Y56.9pMm2Fcj/RALRmZicuA.6dAdWRRuLe9coQI7nNHRyW0YJF8vu', 'user3@example.com', 2000.00);

-- Таблица bank
INSERT INTO bank (name)
VALUES ('Bank A'),
       ('Bank B'),
       ('Bank C');

-- Таблица account
INSERT INTO account (user_id, bank_id, balance, account_number, name_user, opening_time)
VALUES (1, 1, 1500.00, 'ACC123456789', 'User 1 - Bank A', CURRENT_TIMESTAMP),
       (1, 2, 500.00, 'ACC987654321', 'User 1 - Bank B', CURRENT_TIMESTAMP),
       (2, 1, 200.00, 'ACC111111111', 'User 2 - Bank A', CURRENT_TIMESTAMP),
       (3, 3, 2500.00, 'ACC333333333', 'User 3 - Bank C', CURRENT_TIMESTAMP);

-- Таблица transactions
INSERT INTO transactions (sender_account_number, receiver_account_number, transaction_amount, transaction_type,
                          transaction_timestamp)
VALUES ('ACC123456789', 'ACC987654321', 200.00, 'transfer', CURRENT_TIMESTAMP),
       ('ACC987654321', 'ACC123456789', 100.00, 'transfer', CURRENT_TIMESTAMP),
       ('ACC111111111', 'ACC123456789', 50.00, 'transfer', CURRENT_TIMESTAMP),
       ('ACC333333333', 'ACC987654321', 500.00, 'transfer', CURRENT_TIMESTAMP),
       ('ACC123456789', 'ACC111111111', 300.00, 'transfer', CURRENT_TIMESTAMP),
       ('ACC987654321', 'ACC333333333', 150.00, 'transfer', CURRENT_TIMESTAMP);

