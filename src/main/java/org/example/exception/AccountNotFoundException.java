package org.example.exception;

public class AccountNotFoundException extends AbstractExceptionMessageException {

    public AccountNotFoundException(String account) {
        super(String.format("%s not found.", account));
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
