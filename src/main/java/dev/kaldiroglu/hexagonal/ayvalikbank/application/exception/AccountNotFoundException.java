package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) { super(message); }
}
