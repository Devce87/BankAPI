package backend.bankaccount.web.app.exception;

public class BalanceException extends RuntimeException{
    public BalanceException(String message) {
        super(message);
    }
}
