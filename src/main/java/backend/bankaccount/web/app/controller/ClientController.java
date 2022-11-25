package backend.bankaccount.web.app.controller;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.dto.ClientDTO;
import backend.bankaccount.web.app.mapper.ClientMapper;
import backend.bankaccount.web.app.service.repo.AccountService;
import backend.bankaccount.web.app.service.repo.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController {
    private final ClientService<ClientDTO> clientService;
    private final AccountService<AccountDTO> accountService;



    public ClientController(ClientService<ClientDTO> clientService, AccountService<AccountDTO> accountService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @PostMapping()
    public ResponseEntity<?> addClient(@RequestBody @Valid ClientDTO clientDTO){
        clientService.addClient(clientDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @GetMapping
    public List<ClientDTO> getAllClients(){
        return clientService.getAllClients();

    }

    @PostMapping("/{clientId}/account")
    public ResponseEntity<?> addClientAccount(@RequestBody @Valid AccountDTO accountDTO, @PathVariable Long clientId){
        accountService.addAccount(accountDTO, clientId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateClient(@RequestBody @Valid ClientDTO clientDTO, @PathVariable Long id){
        clientService.updateClient(clientDTO,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
