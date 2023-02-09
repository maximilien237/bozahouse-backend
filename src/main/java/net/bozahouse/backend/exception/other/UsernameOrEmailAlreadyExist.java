package net.bozahouse.backend.exception.other;

public class UsernameOrEmailAlreadyExist extends Exception {
    public UsernameOrEmailAlreadyExist(String message) {
        super(message);
    }
}
