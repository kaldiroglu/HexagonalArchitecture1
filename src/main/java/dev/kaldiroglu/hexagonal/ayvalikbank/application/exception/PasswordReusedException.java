package dev.kaldiroglu.hexagonal.ayvalikbank.application.exception;

public class PasswordReusedException extends RuntimeException {
    public PasswordReusedException(String message) { super(message); }
}
