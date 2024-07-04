package org.example.exception;

public class TransactionException extends AbstractExceptionMessageException{
    public TransactionException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
