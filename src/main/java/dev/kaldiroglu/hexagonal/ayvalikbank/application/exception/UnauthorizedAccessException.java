package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) { super(message); }
}
