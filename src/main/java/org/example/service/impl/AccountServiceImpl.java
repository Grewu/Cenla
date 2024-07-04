package org.example.service.impl;

import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.example.service.AccountService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the AccountService interface.
 */
public final class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    /**
     * Constructs a new instance of AccountServiceImpl with the provided AccountRepository.
     *
     * @param accountRepository The repository responsible for account data storage and retrieval.
     */
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void creat(Account account) {
        accountRepository.creat(account);
    }

    @Override
    public void updateBalanceUser(Integer id, BigDecimal newBalance, Integer userId, String accountNumber) {

        accountRepository.updateBalanceUser(id, newBalance, userId, accountNumber);
    }

    @Override
    public List<Account> getAccountsByUserID(Integer id) {
        return accountRepository.getAccountsByUserID(id);
    }

    @Override
    public void updateBalanceAccount(Integer id, BigDecimal newBalance, String numberAccount) {
        accountRepository.updateBalanceAccount(id, newBalance, numberAccount);
    }

    @Override
    public void closeAccount(int numberOfAccount) {
        accountRepository.closeAccount(numberOfAccount);
    }

    @Override
    public String getAccountByNumberOfAccount(int numberOfAccount) {
        return accountRepository.getAccountByNumberOfAccount(numberOfAccount);
    }

}
