package org.example.repository;

import org.example.entity.User;

import java.math.BigDecimal;

/**
 * Interface defining methods for managing user-related operations.
 */
public interface UserRepository {

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     */
    void create(User user);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The retrieved user.
     */
    User getUserByUsername(String username);

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param user     The user for which to check the password.
     * @param password The password to check.
     * @return True if the password is correct, false otherwise.
     */
    boolean checkPassword(User user, String password);

    /**
     * Displays the balance of a user's account.
     *
     * @param user The user whose balance to display.
     */
    void viewBalance(User user);

    /**
     * Checks the available cash of a user.
     *
     * @param user The user to check the cash for.
     * @return The available cash.
     */
    BigDecimal checkCash(User user);

    /**
     * Retrieves the ID of a user.
     *
     * @param user The user whose ID to retrieve.
     * @return The user's ID.
     */
    int getUserId(User user);

    /**
     * Deletes a certain amount of cash from a user's account.
     *
     * @param id             The ID of the user.
     * @param initialBalance The amount to delete.
     */
    void deleteCash(Integer id, BigDecimal initialBalance);

    /**
     * Adds a certain amount of cash to a user's account.
     *
     * @param id             The ID of the user.
     * @param addBalanceUser The amount to add.
     */
    void addCashUser(Integer id, BigDecimal addBalanceUser);
}
