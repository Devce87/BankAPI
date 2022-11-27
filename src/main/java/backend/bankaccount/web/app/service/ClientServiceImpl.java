package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.dto.ClientDTO;
import backend.bankaccount.web.app.domain.entity.Client;
import backend.bankaccount.web.app.exception.CustomNotFoundException;
import backend.bankaccount.web.app.mapper.AccountMapper;
import backend.bankaccount.web.app.mapper.ClientMapper;
import backend.bankaccount.web.app.domain.repo.ClientRepository;
import backend.bankaccount.web.app.service.contract.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Entering method addClient in ClientServiceImpl.");

        try {
            clientRepository.save(clientMapper.DTOtoEntity(clientDTO));
            log.info("Client name: {} added successfully", clientDTO.getName());

        } catch (Exception ex) {
            log.error("An error occurred while trying to save client name: {}", clientDTO.getName(), ex);
        }
    }

    public ClientDTO getClientById(Long id) {
        return clientMapper.entityToDTO(findClientById(id));
    }

    //Review
    public List<ClientDTO> getAllClients() {

        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty())
            throw new CustomNotFoundException("No clients could be retrieved");

        return clients.stream().map(clientMapper::entityToDTO).collect(Collectors.toList());
    }

    public void updateClient(ClientDTO clientDTO, Long clientId) {
        log.info("Entering method updateClient in ClientServiceImpl.");

        try {
            log.info("Updating Client name: {}",clientDTO.getName());

            Client client = findClientById(clientId);
            client.setName(clientDTO.getName());
            client.setAge(client.getAge());
            client.setIdentification(client.getIdentification());
            client.setPassword(clientDTO.getPassword());
            client.setAddress(clientDTO.getAddress());

            if (clientDTO.getAddress() != null)
                client.setAddress(clientDTO.getAddress());

            clientRepository.save(client);
            log.info("Client name: {} updated successfully", clientDTO.getName());

        } catch (Exception ex) {
            log.error("An error occurred while trying to update client name: {}", clientDTO.getName(), ex);
        }
    }

    public void deleteClient(Long id) {
        log.info("Entering method deleteClient in ClientServiceImpl.");

        try {
            clientRepository.delete(findClientById(id));
            log.info("Client deleted successfully");

        } catch (Exception ex) {
            log.error("An error occurred while trying to delete client name: {}", getClientById(id), ex);
        }
    }

    //Review
    public void addClientAccount(AccountDTO accountDTO, Long clientId) {
        log.info("Entering method addClientAccount in ClientServiceImpl.");

        try {

            Client client = findClientById(clientId);
            client.getAccountList().add(accountMapper.DTOtoEntity(accountDTO));
            log.info("Account number: {} deleted successfully",accountDTO.getAccountNumber());
        } catch (Exception ex) {
            log.error("An error occurred while trying to add account number:{} to client name: {}", accountDTO.getAccountNumber(),clientId,ex);
        }
    }

    private Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new CustomNotFoundException("Client not found."));
    }

}
