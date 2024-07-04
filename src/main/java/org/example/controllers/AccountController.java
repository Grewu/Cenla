package org.example.controllers;

import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.example.repository.impl.AccountRepositoryImpl;
import org.example.service.AccountService;
import org.example.service.impl.AccountServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public final class AccountController {
    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private final AccountService accountService = new AccountServiceImpl(accountRepository);

    /**
     * Creates a new account.
     *
     * @param account The account to create.
     */
    public void creat(Account account) {
        accountService.creat(account);
    }

    /**
     * Updates the balance for a user's account.
     *
     * @param id            The ID of the account.
     * @param newBalance    The new balance to set.
     * @param userId        The ID of the user.
     * @param accountNumber The account number.
     */
    public void updateBalanceUser(Integer id, BigDecimal newBalance, Integer userId, String accountNumber) {
        accountService.updateBalanceUser(id, newBalance, userId, accountNumber);
    }

    /**
     * Retrieves a list of accounts associated with a user.
     *
     * @param id The ID of the user.
     * @return A list of accounts.
     */
    public List<Account> getAccountsByUserID(Integer id) {
        return accountService.getAccountsByUserID(id);
    }

    /**
     * Updates the balance for an account.
     *
     * @param id            The ID of the account.
     * @param newBalance    The new balance to set.
     * @param numberAccount The account number.
     */
    public void updateBalanceAccount(Integer id, BigDecimal newBalance, String numberAccount) {
        accountService.updateBalanceAccount(id, newBalance, numberAccount);
    }

    /**
     * Closes an account.
     *
     * @param numberOfAccount The account number to close.
     */
    public void closeAccount(Integer numberOfAccount) {
        accountService.closeAccount(numberOfAccount);
    }

    /**
     * Retrieves an account by its account number.
     *
     * @param numberOfAccount The account number.
     * @return The account as a string.
     */
    public String getAccountByNumberOfAccount(Integer numberOfAccount) {
        return accountService.getAccountByNumberOfAccount(numberOfAccount);
    }

}
