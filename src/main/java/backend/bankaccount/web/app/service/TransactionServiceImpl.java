package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.constants.TransactionType;
import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.domain.entity.Account;
import backend.bankaccount.web.app.domain.entity.Transaction;
import backend.bankaccount.web.app.exception.BalanceException;
import backend.bankaccount.web.app.exception.CustomNotFoundException;
import backend.bankaccount.web.app.mapper.TransactionMapper;
import backend.bankaccount.web.app.domain.repo.AccountRepository;
import backend.bankaccount.web.app.domain.repo.TransactionRepository;
import backend.bankaccount.web.app.service.contract.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService<TransactionDTO> {
    private static final Double DAILY_MAX_WITHDRAWAL = 1000D;
    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }

    public void addTransaction(Long accountId, TransactionDTO transactionDTO) {

        try {
            Transaction transaction = transactionMapper.DTOtoEntity(transactionDTO);
            Account account = accountRepository.findById(accountId).orElseThrow(() -> new CustomNotFoundException("Account not found."));

            if (account.getTransactionList().isEmpty()) {
                account.setBalance(account.getInitialAmount());
            }

            transaction.setAccount(account);

            //Bug fixed: --> removed nested try catch, to avoid saving when transactionManager fails.
            //However, error could work as a transaction attempt logger since it doesn't update account.
            transactionManager(transaction, account);
            log.info("Transaction amount: {} authorized successfully", transactionDTO.getTransactionAmount());

            transactionRepository.save(transaction);

        } catch (CustomNotFoundException ex) {
            log.error("An error occurred while trying to save Transaction amount: {}", transactionDTO.getTransactionAmount(), ex);
            throw new CustomNotFoundException("Account not found");
        }
    }


    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty())
            throw new CustomNotFoundException("There are no transactions on record");

        return transactions.stream().map(transactionMapper::entityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getAllTransactionsByClient(Long clientId) {

        List<TransactionDTO> transactionDTOS = transactionRepository.findAll()
                .stream()
                .filter(transaction ->
                        (transaction.getAccount().getClient().getId().equals(clientId)))
                .map(transactionMapper::entityToDTO).collect(Collectors.toList());

        if (transactionDTOS.isEmpty())
            throw new CustomNotFoundException("Client does not have transactions");

        return transactionDTOS;

    }

    public TransactionDTO getTransactionById(Long id) {
        return transactionMapper.entityToDTO(findTransactionById(id));
    }

    private Transaction findTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Transaction not found."));

    }

    private void transactionManager(Transaction transaction, Account account) {
        log.info("Transaction Manager");

        double withdrawalsToday = getWithdrawalsToday();

        //Deposits
        if (transaction.getTransactionType().equals(TransactionType.Deposit)) {
            log.info("1");

            account.setBalance(account.getBalance() + transaction.getTransactionAmount());
            transaction.setAccountBalance(account.getBalance());

        } else if (transaction.getTransactionType().equals(TransactionType.Withdrawal)) {
            log.info("Checking withdrawals.");
            //Withdrawals

            if (account.getBalance() == 0) {
                throw new BalanceException("No balance available.");

            } else if (account.getBalance() - transaction.getTransactionAmount() < 0) {
                throw new BalanceException("Insufficient funds.");

            } else if (withdrawalsToday - transaction.getTransactionAmount() > DAILY_MAX_WITHDRAWAL) {
                throw new BalanceException("You have exceeded the max withdrawal amount " + DAILY_MAX_WITHDRAWAL + " today.");

            } else if (account.getBalance() - transaction.getTransactionAmount() >= 0) {
                log.info("Withdrawing balance.");
                account.setBalance(account.getBalance() - transaction.getTransactionAmount());
                transaction.setAccountBalance(account.getBalance());
            }
        }
    }


    private double getWithdrawalsToday() {
        double withdrawalsToday = 0;

        if (!transactionRepository.findAll().isEmpty()) {
            withdrawalsToday = getAllTransactions().stream()
                    .filter(tDate -> (LocalDate.parse(tDate.getDate())).isEqual(LocalDate.now()))
                    .filter(tType -> tType.getTransactionType().equals(TransactionType.Withdrawal))
                    .map(TransactionDTO::getTransactionAmount)
                    .reduce((double) 0, Double::sum);
        }
        return withdrawalsToday;
    }

}

