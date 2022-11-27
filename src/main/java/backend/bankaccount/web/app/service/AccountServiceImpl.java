package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.entity.Account;
import backend.bankaccount.web.app.exception.CustomNotFoundException;
import backend.bankaccount.web.app.mapper.AccountMapper;
import backend.bankaccount.web.app.domain.repo.AccountRepository;
import backend.bankaccount.web.app.domain.repo.ClientRepository;
import backend.bankaccount.web.app.service.contract.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
        log.info("Entering method addAccount in ClientServiceImpl.");

        try {
            Account account = accountMapper.DTOtoEntity(accountDTO);
            account.setBalance(account.getInitialAmount());
            account.setClient(clientRepository.findById(clientId).orElseThrow(()->new CustomNotFoundException("Client not found")));

            accountRepository.save(account);
            log.info("Account number: {} added successfully", accountDTO.getAccountNumber());

        } catch (CustomNotFoundException ex) {
            log.error("An error occurred while trying to add Account number: {}", accountDTO.getAccountNumber(),ex);
            throw new CustomNotFoundException("Client not found");

        }
    }

    public AccountDTO getAccountById(Long id) {
        return accountMapper.entityToDTO(accountRepository.findById(id)
                .orElseThrow(()->new CustomNotFoundException("Account not found")));

    }


    public List<AccountDTO> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();

        if(accounts.isEmpty())
            throw new CustomNotFoundException("No accounts could be retrieved.");

        return accounts.stream().map(accountMapper::entityToDTO).collect(Collectors.toList());
    }


    public void updateAccount(AccountDTO accountDTO, Long id) {
        log.info("Entering method updateAccount in ClientServiceImpl.");
        Account account = null;

        try {
            account = findAccountById(id);
            // Logically, none of the attributes in account should be updated except for the status
            // transactions will modify balance
            account.setStatus(accountDTO.getStatus());
            log.info("Account number: {} status has been updated successfully to: {}", accountDTO.getAccountNumber(),accountDTO.getStatus());
            accountRepository.save(account);

        } catch (Exception ex) {
            log.error("An error occurred while trying to update account number: {} to status: {}", accountDTO.getAccountNumber(), accountDTO.getStatus(), ex);
            throw new CustomNotFoundException("Account not found");
        }


    }

    public void deleteAccount(Long id) {
        log.info("Entering method deleteAccount in ClientServiceImpl.");

        try {
            accountRepository.delete(findAccountById(id));
            log.info("Account deleted successfully");

        } catch (Exception ex) {
            log.error("An error occurred while trying to update client name: {}", getAccountById(id), ex);
            throw new CustomNotFoundException("Account not found");
        }
    }

    private Account findAccountById(Long id){
        return accountRepository.findById(id).orElseThrow(()->new CustomNotFoundException("Account not found"));
    }
}
