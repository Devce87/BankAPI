package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.constants.TransactionType;
import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.domain.entity.Account;
import backend.bankaccount.web.app.domain.entity.Transaction;
import backend.bankaccount.web.app.exception.ExceptionMessage;
import backend.bankaccount.web.app.mapper.TransactionMapper;
import backend.bankaccount.web.app.repo.AccountRepository;
import backend.bankaccount.web.app.repo.TransactionRepository;
import backend.bankaccount.web.app.service.repo.TransactionService;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService<TransactionDTO> {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }


    public void addTransaction(Long accountId, TransactionDTO transactionDTO) {

        Transaction transaction = transactionMapper.DTOtoEntity(transactionDTO);
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ExceptionMessage("Account not found."));
        transaction.setAccount(account);

        transactionManager(transaction, account);

        transactionRepository.save(transaction);

    }


    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty())
            throw new ExceptionMessage("There are no transactions on record");

        return transactions.stream().map(transactionMapper::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByClient(Long clientId) {

        return transactionRepository.findAll()
                .stream()
                .filter(transaction ->
                        transaction.getAccount().getClient().getId().equals(clientId))
                .map(transactionMapper::entityToDTO).collect(Collectors.toList());

    }

    public TransactionDTO getTransactionById(Long id) {
        return transactionMapper.entityToDTO(findTransactionById(id));
    }

    private Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ExceptionMessage("Transaction not found."));

    }

    public void transactionManager(Transaction transaction, Account account){

        if (transaction.getTransactionType().equals(TransactionType.Deposit)) {
            account.setBalance(account.getBalance() + transaction.getTransactionAmount());
            transaction.setAccountBalance(account.getBalance());

        } else if (transaction.getTransactionType().equals(TransactionType.Withdrawal)) {

            double dailyMaxWithdrawals = 1000D;

            Double withdrawalsToday = getAllTransactions().stream()
                    .filter(tDate -> (LocalDate.parse(tDate.getDate())).isEqual(LocalDate.now()))
                    .filter(tType -> tType.getTransactionType().equals(TransactionType.Withdrawal))
                    .map(TransactionDTO::getTransactionAmount)
                    .reduce((double) 0, Double::sum);

            if (account.getBalance() == 0) {
                throw new ExceptionMessage("No balance available.");

            } else if (account.getBalance() - transaction.getTransactionAmount() < 0) {
                throw new ExceptionMessage("Insufficient funds.");

            } else if ((withdrawalsToday - transaction.getTransactionAmount()) > dailyMaxWithdrawals) {
                throw new ExceptionMessage("Max daily withdrawal amount exceeded.");

            } else if (account.getBalance() - transaction.getTransactionAmount() >= 0) {
                account.setBalance(account.getBalance() - transaction.getTransactionAmount());
                transaction.setAccountBalance(account.getBalance());

            }

        }
    }

}

