package backend.bankaccount.web.app.controller;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.service.contract.AccountService;
import backend.bankaccount.web.app.service.contract.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/account")
public class AccountController {

    private final AccountService<AccountDTO> accountService;

    private final TransactionService<TransactionDTO> transactionService;

    public AccountController(AccountService<AccountDTO> accountService, TransactionService<TransactionDTO> transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;

    }

    @GetMapping
    public List<AccountDTO> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id);
    }


    @PostMapping("/{accountId}/transaction")
    public ResponseEntity<?> addAccountTransaction(@PathVariable Long accountId, @RequestBody @Valid TransactionDTO transactionDTO){
        transactionService.addTransaction(accountId, transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable Long id){
        accountService.updateAccount(accountDTO, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
