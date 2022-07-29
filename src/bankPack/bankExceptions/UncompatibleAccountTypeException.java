package bankPack.bankExceptions;

public class UncompatibleAccountTypeException extends Exception {
    public UncompatibleAccountTypeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
