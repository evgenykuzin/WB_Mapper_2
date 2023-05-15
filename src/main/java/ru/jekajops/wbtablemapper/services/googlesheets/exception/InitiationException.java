package ru.jekajops.wbtablemapper.services.googlesheets.exception;

public class InitiationException extends Exception {
    public InitiationException(String message) {
        super(message);
    }

    public InitiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitiationException(Throwable cause) {
        super(cause);
    }
}