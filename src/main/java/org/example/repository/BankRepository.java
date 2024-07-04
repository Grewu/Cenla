package org.example.repository;

/**
 * Interface defining methods for managing banks.
 */
public interface BankRepository {

    /**
     * Displays the IDs of available banks.
     */
    void viewBanksID();

    /**
     * Retrieves the name of a bank by its ID.
     *
     * @param bankNumber The ID of the bank.
     * @return The name of the bank.
     */
    String getBankById(Integer bankNumber);
}
