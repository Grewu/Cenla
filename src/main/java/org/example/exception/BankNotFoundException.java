package org.example.exception;

public class BankNotFoundException extends AbstractExceptionMessageException {

    public BankNotFoundException(String bank) {
        super(String.format("%s not found.", bank));
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
