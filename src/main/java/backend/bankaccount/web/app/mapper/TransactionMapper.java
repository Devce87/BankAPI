package backend.bankaccount.web.app.mapper;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.domain.entity.Transaction;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Component
public class TransactionMapper implements DefaultMapper<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO entityToDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .date(transaction.getDate().toString())
                .transactionType(transaction.getTransactionType())
                .transactionAmount(transaction.getTransactionAmount())
                .accountBalance(transaction.getAccountBalance())
                .build();
    }

    @Override
    public Transaction DTOtoEntity(TransactionDTO transactionDTO) {
        return Transaction.builder()
                .date(LocalDate.parse(transactionDTO.getDate()))
                .transactionType(transactionDTO.getTransactionType())
                .transactionAmount(transactionDTO.getTransactionAmount())
                .accountBalance(transactionDTO.getAccountBalance())
                .build();
    }
}