package net.bozahouse.backend.exception.other;


import net.bozahouse.backend.exception.form.ValidationFormException;

public class WrongUrlException extends ValidationFormException {

    public WrongUrlException(String message) {
        super(message);
    }
}
