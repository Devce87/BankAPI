package backend.bankaccount.web.app.service.contract;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.dto.ClientDTO;


import java.util.List;

public interface ClientService<T> {

    void addClient(T client);

    T getClientById(Long id);

    List<T> getAllClients();

    void updateClient(ClientDTO clientDTO, Long id);

    void deleteClient(Long id);

//    void addClientAccount(AccountDTO accountDTO, Long clientId);


}
