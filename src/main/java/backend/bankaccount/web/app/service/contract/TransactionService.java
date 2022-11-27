package backend.bankaccount.web.app.service.contract;

import java.util.List;

public interface TransactionService<T>{

    void addTransaction(Long accountId, T transaction);

    T getTransactionById(Long id);

    List<T> getAllTransactions();

    List<T> getAllTransactionsByClient(Long clientId);
}
