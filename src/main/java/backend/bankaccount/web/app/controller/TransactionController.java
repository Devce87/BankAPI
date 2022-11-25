package backend.bankaccount.web.app.controller;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.mapper.TransactionMapper;
import backend.bankaccount.web.app.service.repo.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/transaction")
public class TransactionController {

    private final TransactionService<TransactionDTO> transactionService;


    public TransactionController(TransactionService<TransactionDTO> transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
    }

    @GetMapping(value= "{id}")
    public TransactionDTO findTransactionById(@PathVariable Long id){
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @GetMapping(value = "{id}/client")
    public List<TransactionDTO> getAllTransactionsByClient(@PathVariable Long id){
        return transactionService.getAllTransactionsByClient(id);
    }


}
