package org.ristorante.service.custom_error;

public class ValidationError extends RuntimeException {
    public ValidationError(String message) {
        super(message);
    }
}
