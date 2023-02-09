package net.bozahouse.backend.exception.other;


import net.bozahouse.backend.exception.form.ValidationFormException;

public class EmptyBlankException extends ValidationFormException {

    public EmptyBlankException(String message) {
        super(message);
    }
}
