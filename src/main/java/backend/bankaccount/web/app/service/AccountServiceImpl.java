package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.entity.Account;
import backend.bankaccount.web.app.exception.AccountNotFoundException;
import backend.bankaccount.web.app.exception.ExceptionMessage;
import backend.bankaccount.web.app.mapper.AccountMapper;
import backend.bankaccount.web.app.repo.AccountRepository;
import backend.bankaccount.web.app.repo.ClientRepository;
import backend.bankaccount.web.app.service.repo.AccountService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService<AccountDTO> {

    private final AccountRepository accountRepository;

    private final ClientRepository clientRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, ClientRepository clientRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.accountMapper = accountMapper;

    }

    public void addAccount(AccountDTO accountDTO, Long clientId) {

        Account account = accountMapper.DTOtoEntity(accountDTO);
        account.setClient(clientRepository.findById(clientId).orElseThrow(()->new ExceptionMessage("Not client found for given id")));

        accountRepository.save(account);
    }

    public AccountDTO getAccountById(Long id) {
        return accountMapper.entityToDTO(accountRepository.findById(id)
                .orElseThrow(()->new ExceptionMessage("Not found")));

    }


    public List<AccountDTO> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty())
            throw new ExceptionMessage("There are no Accounts yet.");

        return accounts.stream().map(accountMapper::entityToDTO).collect(Collectors.toList());
    }


    public void updateAccount(AccountDTO accountDTO, Long id) {
        Account account = findAccountById(id);
        // Logically, none of the attributes in account should be updated except for the status
        account.setStatus(accountDTO.getStatus());

        accountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        accountRepository.delete(findAccountById(id));
    }

    private Account findAccountById(Long id){
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }
}
