package Helpers;

public class EncryptException extends Exception {


    public EncryptException(String errorMessage) {
        super(errorMessage);
    }

    public EncryptException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
