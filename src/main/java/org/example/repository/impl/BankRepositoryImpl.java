package org.example.repository.impl;

import org.example.repository.BankRepository;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the BankRepository interface.
 */
public class BankRepositoryImpl implements BankRepository {

    private static final Connection connection = ConnectionManager.open();

    // SQL query constants
    private static final String SELECT_BANKS_QUERY = "SELECT id, name FROM bank";
    private static final String SELECT_BANK_BY_ID_QUERY = "SELECT * FROM bank WHERE id = ?";

    @Override
    public void viewBanksID() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BANKS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String bankName = resultSet.getString("name");
                int bankId = resultSet.getInt("id");
                System.out.println(bankId + " " + bankName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBankById(Integer bankNumber) {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BANK_BY_ID_QUERY)) {
            statement.setInt(1, bankNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
