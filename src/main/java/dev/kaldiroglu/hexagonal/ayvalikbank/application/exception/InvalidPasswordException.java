package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) { super(message); }
}
