# Clever-Bank Console Application

Welcome to the Console-Bank console application! This project provides the functionality of a banking system, allowing
you to manage accounts, perform transactions, and much more.

## Main Entities

- Bank
- Account
- User
- Transaction

## Tech Stack

- Java 17
- Gradle
- PostgreSQL
- JDBC
- Lombok

## Key Features

- View user balance.
- View a list of available banks.
- Open a new bank account.
- View user account information.
- Deposit funds into an account.
- Withdraw funds from an account.
- Top up user balance.
- Transfer funds between accounts, including accounts in other banks.
- Close a bank account.

## Running the Application

1. Install Java 17, Gradle, and PostgreSQL if they are not already installed.
2. Run data.sql
5. Edit the `src/main/resources/application.yml` file to configure the database connection.
6. Build the project: gradle build
7. Run the application: gradle run
