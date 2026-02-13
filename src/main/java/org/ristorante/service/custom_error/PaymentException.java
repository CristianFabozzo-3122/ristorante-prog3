package org.ristorante.service.custom_error;

public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }
}
