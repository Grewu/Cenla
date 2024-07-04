package org.example.service.impl;

import org.example.repository.BankRepository;
import org.example.service.BankService;

/**
 * Implementation of the BankService interface.
 */
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;

    /**
     * Constructs a new instance of BankServiceImpl with the provided BankRepository.
     *
     * @param bankRepository The repository responsible for bank data storage and retrieval.
     */
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public void viewBanksID() {
        bankRepository.viewBanksID();
    }

    @Override
    public String getBankById(Integer bankNumber) {
        return bankRepository.getBankById(bankNumber);
    }
}
