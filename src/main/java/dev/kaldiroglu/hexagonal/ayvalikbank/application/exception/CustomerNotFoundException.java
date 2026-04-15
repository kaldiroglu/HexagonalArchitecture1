package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) { super(message); }
}
