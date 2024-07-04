package org.example.repository;

import org.example.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface defining methods for managing accounts.
 */
public interface AccountRepository {

    /**
     * Creates a new account.
     *
     * @param account The account to be created.
     */
    void creat(Account account);

    /**
     * Updates the balance of a user's account.
     *
     * @param id            The ID of the account.
     * @param newBalance    The new balance to set.
     * @param userId        The ID of the user.
     * @param accountNumber The account number.
     */
    void updateBalanceUser(Integer id, BigDecimal newBalance, int userId, String accountNumber);

    /**
     * Retrieves a list of accounts associated with a user.
     *
     * @param id The ID of the user.
     * @return A list of user's accounts.
     */
    List<Account> getAccountsByUserID(Integer id);

    /**
     * Updates the balance of an account.
     *
     * @param id            The ID of the account.
     * @param newBalance    The new balance to set.
     * @param numberAccount The account number.
     */
    void updateBalanceAccount(Integer id, BigDecimal newBalance, String numberAccount);

    /**
     * Closes an account.
     *
     * @param numberOfAccount The number of the account to close.
     */
    void closeAccount(Integer numberOfAccount);

    /**
     * Retrieves account information by account number.
     *
     * @param numberOfAccount The number of the account to retrieve.
     * @return Account information as a string.
     */
    String getAccountByNumberOfAccount(Integer numberOfAccount);

    /**
     * Retrieves the opening date of an account.
     *
     * @param numberOfAccount The number of the account.
     * @return The opening date as a string.
     */
    String getOpeningDate(Integer numberOfAccount);
}
