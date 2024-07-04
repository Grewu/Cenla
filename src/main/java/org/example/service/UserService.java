package org.example.service;

import org.example.entity.User;

import java.math.BigDecimal;

/**
 * Service interface for user operations.
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     *
     * @param user Data of the new user.
     */
    void create(User user);

    /**
     * Gets a user by their username.
     *
     * @param username The username of the user.
     * @return The found user or null if not found.
     */
    User getUserByUsername(String username);

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param user     The user to check the password for.
     * @param password The password to be checked.
     * @return true if the password is correct, otherwise false.
     */
    boolean checkPassword(User user, String password);

    /**
     * Records the user's balance in the event log.
     *
     * @param user The user whose balance needs to be logged.
     */
    void viewBalance(User user);

    /**
     * Checks the current cash balance of a user.
     *
     * @param user The user to check the balance for.
     * @return The current cash balance of the user.
     */
    BigDecimal checkCash(User user);

    /**
     * Retrieves the user's identifier.
     *
     * @param user The user to get the identifier for.
     * @return The user's identifier.
     */
    int getUserId(User user);

    /**
     * Deducts a specified amount of cash from a user's balance.
     *
     * @param id             The user's identifier.
     * @param initialBalance The initial balance of the user.
     */
    void deleteCash(Integer id, BigDecimal initialBalance);

    /**
     * Adds a specified amount of cash to a user's balance.
     *
     * @param userId         The user's identifier.
     * @param addBalanceUser The amount to add to the user's balance.
     */
    void addCashUser(Integer userId, BigDecimal addBalanceUser);
}
