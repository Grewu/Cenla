package org.example.repository.impl;

import org.example.entity.Account;
import org.example.exception.TransactionException;
import org.example.repository.AccountRepository;
import org.example.util.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AccountRepository interface.
 */
public final class AccountRepositoryImpl implements AccountRepository {
    private static final Connection connection = ConnectionManager.open();

    private static final String CREATE_ACCOUNT_QUERY = "INSERT INTO account (user_id, bank_id, balance, account_number, name_user, opening_time) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_PERSON_CASH_QUERY = "UPDATE person SET cash = cash - ? WHERE id = ?";
    private static final String INSERT_TRANSACTION_QUERY = "INSERT INTO transactions (sender_account_number, receiver_account_number, transaction_amount, transaction_timestamp, transaction_type) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ACCOUNTS_BY_USER_ID_QUERY = "SELECT * FROM account WHERE user_id = ? AND closing_time IS NULL";
    private static final String UPDATE_ACCOUNT_BALANCE_QUERY = "UPDATE account SET balance = balance - ? WHERE id = ?";
    private static final String UPDATE_PERSON_CASH_BY_ID_QUERY = "UPDATE person SET cash = cash + ? WHERE id = ?";
    private static final String SELECT_USER_ID_BY_ACCOUNT_ID_QUERY = "SELECT user_id FROM account WHERE id = ?";
    private static final String SELECT_ACCOUNT_BALANCE_QUERY = "SELECT balance FROM account WHERE id = ?";
    private static final String UPDATE_ACCOUNT_CLOSING_TIME_QUERY = "UPDATE account SET closing_time = ? WHERE id = ?";
    private static final String SELECT_ACCOUNT_BY_ID_QUERY = "SELECT * FROM account WHERE id = ?";

    @Override
    public void creat(Account account) {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_ACCOUNT_QUERY)) {
            statement.setInt(1, account.getIdUser());
            statement.setInt(2, account.getIdBank());
            statement.setBigDecimal(3, account.getBalance());
            statement.setString(4, account.getAccountNumber());
            statement.setString(5, account.getBankName());
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            statement.execute();
            System.out.println("The account has been successfully created.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateBalanceUser(Integer id, BigDecimal newBalance, int userId, String accountNumber) {
        try {
            updatePersonCash(userId, newBalance);
            updateAccountBalance(id, newBalance);
            insertTransaction(accountNumber, accountNumber, newBalance, "update balance");
            System.out.println("The balance has been successfully updated.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePersonCash(int userId, BigDecimal newBalance) throws SQLException {
        try (PreparedStatement personUpdateStatement = connection.prepareStatement(UPDATE_PERSON_CASH_QUERY)) {
            personUpdateStatement.setBigDecimal(1, newBalance);
            personUpdateStatement.setInt(2, userId);
            personUpdateStatement.executeUpdate();
        }
    }

    private void insertTransaction(String senderAccountNumber, String receiverAccountNumber, BigDecimal transactionAmount,
                                   String transactionType) throws SQLException {
        try (PreparedStatement transactionInsertStatement = connection.prepareStatement(INSERT_TRANSACTION_QUERY)) {
            transactionInsertStatement.setString(1, senderAccountNumber);
            transactionInsertStatement.setString(2, receiverAccountNumber);
            transactionInsertStatement.setBigDecimal(3, transactionAmount);
            transactionInsertStatement.setTimestamp(4,Timestamp.valueOf(LocalDateTime.now()));
            transactionInsertStatement.setString(5, transactionType);
            transactionInsertStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new TransactionException(e.getMessage());
        }
    }

    @Override
    public List<Account> getAccountsByUserID(Integer userID) {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNTS_BY_USER_ID_QUERY)) {
            statement.setInt(1, userID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int user_Id = resultSet.getInt("user_id");
                    int bank_Id = resultSet.getInt("bank_id");
                    BigDecimal balance = resultSet.getBigDecimal("balance");
                    String bankName = resultSet.getString("name_user");
                    Account account = new Account(id, balance, user_Id, bank_Id, bankName);
                    accounts.add(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public void updateBalanceAccount(Integer id, BigDecimal newBalance, String accountNumber) {
        try {
            updateAccountBalance(id, newBalance);
            updatePersonCash(id, newBalance);
            insertTransaction(accountNumber, accountNumber, newBalance, "withdraw");
            System.out.println("The balance has been updated.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateAccountBalance(Integer id, BigDecimal newBalance) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE_QUERY)) {
            statement.setBigDecimal(1, newBalance);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    private void updatePersonCash(Integer id, BigDecimal newBalance) throws SQLException {
        try (PreparedStatement getUserIdStatement = connection.prepareStatement(SELECT_USER_ID_BY_ACCOUNT_ID_QUERY);
             PreparedStatement updatePersonStatement = connection.prepareStatement(UPDATE_PERSON_CASH_BY_ID_QUERY)) {
            getUserIdStatement.setInt(1, id);
            ResultSet resultSet = getUserIdStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                updatePersonStatement.setBigDecimal(1, newBalance);
                updatePersonStatement.setInt(2, userId);
                updatePersonStatement.executeUpdate();
            }
        }
    }

    @Override
    public void closeAccount(Integer numberOfAccount) {
        try {
            BigDecimal balance = retrieveAccountBalance(connection, numberOfAccount);
            if (balance != null) {
                var userId = retrieveUserId(connection, numberOfAccount);
                updatePersonCash(connection, userId, balance);
                deleteAccount(connection, numberOfAccount);
                System.out.println("Account closed successfully");
            } else {
                System.out.println("Account not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error " + e.getMessage());
        }
    }

    private BigDecimal retrieveAccountBalance(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement getBalanceStatement = connection.prepareStatement(SELECT_ACCOUNT_BALANCE_QUERY)) {
            getBalanceStatement.setInt(1, accountId);
            ResultSet balanceResult = getBalanceStatement.executeQuery();
            if (balanceResult.next()) {
                return balanceResult.getBigDecimal("balance");
            }
            return null;
        }
    }

    private Integer retrieveUserId(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement getUserIdStatement = connection.prepareStatement(SELECT_USER_ID_BY_ACCOUNT_ID_QUERY)) {
            getUserIdStatement.setInt(1, accountId);
            ResultSet resultSet = getUserIdStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
            return 0;
        }
    }

    private void updatePersonCash(Connection connection, Integer userId, BigDecimal balance) throws SQLException {
        try (PreparedStatement updatePersonStatement = connection.prepareStatement(UPDATE_PERSON_CASH_BY_ID_QUERY)) {
            updatePersonStatement.setBigDecimal(1, balance);
            updatePersonStatement.setInt(2, userId);
            updatePersonStatement.executeUpdate();
        }
    }

    private void deleteAccount(Connection connection, int accountId) throws SQLException {
        try (PreparedStatement deleteAccountStatement = connection.prepareStatement(UPDATE_ACCOUNT_CLOSING_TIME_QUERY)) {
            deleteAccountStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            deleteAccountStatement.setInt(2, accountId);
            deleteAccountStatement.executeUpdate();
        }
    }

    @Override
    public String getAccountByNumberOfAccount(Integer numberOfAccount) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID_QUERY)) {
            statement.setInt(1, numberOfAccount);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("account_number");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public String getOpeningDate(Integer numberOfAccount) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID_QUERY)) {
            statement.setInt(1, numberOfAccount);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("opening_time");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
