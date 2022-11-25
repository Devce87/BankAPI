package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.dto.ClientDTO;
import backend.bankaccount.web.app.domain.entity.Client;
import backend.bankaccount.web.app.exception.ClientNotFoundException;
import backend.bankaccount.web.app.exception.ExceptionMessage;
import backend.bankaccount.web.app.mapper.AccountMapper;
import backend.bankaccount.web.app.mapper.ClientMapper;
import backend.bankaccount.web.app.domain.repo.ClientRepository;
import backend.bankaccount.web.app.service.repo.ClientService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService<ClientDTO> {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    private final AccountMapper accountMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper, AccountMapper accountMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.accountMapper = accountMapper;
    }

    public void addClient(ClientDTO clientDTO) {
        clientRepository.save(clientMapper.DTOtoEntity(clientDTO));
    }

    public ClientDTO getClientById(Long id) {
        return clientMapper.entityToDTO(findClientById(id));
    }

    //Review
    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        if(clients.isEmpty())
            throw new ExceptionMessage("There are no Clients yet.");

        return clients.stream().map(clientMapper::entityToDTO).collect(Collectors.toList());
    }

    public void updateClient(ClientDTO clientDTO, Long clientId) {

        Client client = findClientById(clientId);
        client.setName(clientDTO.getName());
        client.setAge(client.getAge());
        client.setIdentification(client.getIdentification());
        client.setPassword(clientDTO.getPassword());
        client.setAddress(clientDTO.getAddress());

        if (clientDTO.getAddress() != null)
            client.setAddress(clientDTO.getAddress());

        clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.delete(findClientById(id));
    }

    //Review
    public void addClientAccount(AccountDTO accountDTO, Long clientId) {
        Client client = findClientById(clientId);
        client.getAccountList().add(accountMapper.DTOtoEntity(accountDTO));
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(ClientNotFoundException::new);
    }

}
