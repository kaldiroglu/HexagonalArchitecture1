package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException(String message) {
        super(message);
    }
}
