package pl.twino.microloan.exception;

public class LoanDoesNotExistException extends RuntimeException{

    public LoanDoesNotExistException(String message) {
        super(message);
    }
}
