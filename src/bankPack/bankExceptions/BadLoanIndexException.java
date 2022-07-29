package bankPack.bankExceptions;

public class BadLoanIndexException extends Exception{
    public BadLoanIndexException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
