package org.example.repository.impl;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.util.ConnectionManager;
import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the UserRepository interface.
 */
public class UserRepositoryImpl implements UserRepository {

    private static final Connection connection = ConnectionManager.open();

    // SQL query constants
    private static final String CREATE_USER_QUERY = "INSERT INTO person (username, password, email, cash) VALUES (?, ?, ?, ?)";
    private static final String GET_USER_BY_USERNAME_QUERY = "SELECT * FROM person WHERE username = ?";
    private static final String VIEW_BALANCE_QUERY = "SELECT * FROM person WHERE username = ?";
    private static final String CHECK_CASH_QUERY = "SELECT * FROM person WHERE username = ?";
    private static final String GET_USER_ID_QUERY = "SELECT id FROM person WHERE username = ?";
    private static final String DELETE_CASH_QUERY = "UPDATE person SET cash = cash - ? WHERE id = ?";
    private static final String ADD_CASH_USER_QUERY = "UPDATE person SET cash = cash + ? WHERE id = ?";

    @Override
    public void create(User user) {
        try {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            try (PreparedStatement statement = connection.prepareStatement(CREATE_USER_QUERY)) {
                statement.setString(1, user.getUsername());
                statement.setString(2, hashedPassword);
                statement.setString(3, user.getEmail());
                statement.setBigDecimal(4, user.getCash());
                statement.execute();
                System.out.println("User has been created.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_BY_USERNAME_QUERY)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean checkPassword(User user, String password) {
        String hashedPasswordFromDB = user.getPassword();
        return BCrypt.checkpw(password, hashedPasswordFromDB);
    }

    @Override
    public void viewBalance(User user) {
        try (PreparedStatement statement = connection.prepareStatement(VIEW_BALANCE_QUERY)) {
            statement.setString(1, user.getUsername());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    BigDecimal cash = resultSet.getBigDecimal("cash");
                    System.out.println("Your balance is " + cash);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BigDecimal checkCash(User user) {
        try (PreparedStatement statement = connection.prepareStatement(CHECK_CASH_QUERY)) {
            statement.setString(1, user.getUsername());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBigDecimal("cash");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public int getUserId(User user) {
        try (PreparedStatement statement = connection.prepareStatement(GET_USER_ID_QUERY)) {
            statement.setString(1, user.getUsername());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    @Override
    public void deleteCash(Integer id, BigDecimal initialBalance) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CASH_QUERY)) {
            statement.setBigDecimal(1, initialBalance);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Deducted from balance: " + initialBalance);
            } else {
                System.out.println("Failed to update balance.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCashUser(Integer id, BigDecimal addBalanceUser) {
        try (PreparedStatement statement = connection.prepareStatement(ADD_CASH_USER_QUERY)) {
            statement.setBigDecimal(1, addBalanceUser);
            statement.setInt(2, id);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Balance was increased by: " + addBalanceUser);
            } else {
                System.out.println("Failed to update balance.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
