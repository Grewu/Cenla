package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface defining methods for managing transactions.
 */
public interface TransactionRepository {
    /**
     * Initiates a transfer from one bank to another.
     *
     * @param senderAccountId      The sender's account ID.
     * @param transferAmount       The amount to transfer.
     * @param receivingAccountNumber The receiving account's number.
     */
    void getTransferToOtherBank(String senderAccountId, BigDecimal transferAmount, String receivingAccountNumber);

    /**
     * Saves a transaction record.
     *
     * @param transaction The transaction to be saved.
     */
    void save(Transaction transaction);

    /**
     * Deposits an amount into a user's account.
     *
     * @param accountId     The ID of the account.
     * @param interestAmount The amount to deposit.
     */
    void deposit(Integer accountId, BigDecimal interestAmount);

    /**
     * Retrieves a list of all accounts.
     *
     * @return A list of all accounts.
     */
    List<Account> getAllAccounts();

    /**
     * Retrieves the name of the bank associated with a sender's account.
     *
     * @param senderBankAccount The sender's bank account number.
     * @return The name of the bank.
     */
    String getNameOfBank(String senderBankAccount);

    /**
     * Retrieves a list of transactions for a specific account.
     *
     * @param accountNumber The account number for which transactions should be retrieved.
     * @return A list of transactions.
     */
    List<Transaction> getTransactions(String accountNumber);
}
