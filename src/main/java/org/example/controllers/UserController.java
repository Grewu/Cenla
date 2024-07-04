package org.example.controllers;

import org.example.entity.User;
import org.example.repository.UserRepository;
import org.example.repository.impl.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import java.math.BigDecimal;

/**
 * Controller class for user-related operations.
 */
public class UserController {
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserService userService = new UserServiceImpl(userRepository);

    /**
     * Creates a new user.
     *
     * @param user The user to be created.
     */
    public void create(User user) {
        userService.create(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The retrieved user.
     */
    public User getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    /**
     * Checks if the provided password matches the user's password.
     *
     * @param user     The user for which to check the password.
     * @param password The password to check.
     * @return True if the password is correct, false otherwise.
     */
    public boolean checkPassword(User user, String password) {
        return userService.checkPassword(user, password);
    }

    /**
     * Retrieves the balance of a user's account.
     *
     * @param user The user whose balance to retrieve.
     */

    public void viewBalance(User user) {
        userService.viewBalance(user);
    }

    /**
     * Checks the available cash of a user.
     *
     * @param user The user to check the cash for.
     * @return The available cash.
     */
    public BigDecimal checkCash(User user) {
        return userService.checkCash(user);
    }

    /**
     * Retrieves the ID of a user.
     *
     * @param user The user whose ID to retrieve.
     * @return The user's ID.
     */
    public int getUserId(User user) {
        return userService.getUserId(user);
    }

    /**
     * Deletes a certain amount of cash from a user's account.
     *
     * @param id             The ID of the user.
     * @param initialBalance The amount to delete.
     */
    public void deleteCash(Integer id, BigDecimal initialBalance) {
        userService.deleteCash(id, initialBalance);
    }

    /**
     * Adds a certain amount of cash to a user's account.
     *
     * @param userId         The ID of the user.
     * @param addBalanceUser The amount to add.
     */
    public void addCashUser(Integer userId, BigDecimal addBalanceUser) {
        userService.addCashUser(userId, addBalanceUser);
    }
}
