package backend.bankaccount.web.app.exception;

public class NoTransactionsForDateRange extends RuntimeException{

    public NoTransactionsForDateRange(String message) {
        super(message);
    }
}
