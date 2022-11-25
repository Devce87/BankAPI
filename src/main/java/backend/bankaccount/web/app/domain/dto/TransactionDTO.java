package backend.bankaccount.web.app.domain.dto;

import backend.bankaccount.web.app.constants.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TransactionDTO {

    private String date;
    private TransactionType transactionType;
    private Double transactionAmount;
    private Double accountBalance;
}
