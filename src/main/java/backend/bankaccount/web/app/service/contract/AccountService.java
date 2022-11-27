package backend.bankaccount.web.app.service.contract;



import java.util.List;


public interface AccountService<T> {

    void addAccount(T accountDTO, Long clientId);

    T getAccountById(Long id);

    List<T> getAllAccounts();

    void updateAccount(T accountDTO, Long id);

    void deleteAccount(Long id);
}
