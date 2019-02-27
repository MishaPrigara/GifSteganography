package Helpers;

public class DecryptException extends Exception {


    public DecryptException(String errorMessage) {
        super(errorMessage);
    }

    public DecryptException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
