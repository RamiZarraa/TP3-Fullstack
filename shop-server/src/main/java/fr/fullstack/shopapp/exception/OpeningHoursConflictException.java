package fr.fullstack.shopapp.exception;

public class OpeningHoursConflictException extends RuntimeException {
    public OpeningHoursConflictException(String message) {
        super(message);
    }
}