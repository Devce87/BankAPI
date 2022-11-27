package backend.bankaccount.web.app.domain.dto;

import backend.bankaccount.web.app.constants.TransactionType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDTO {

    @NotBlank
    private String date;

    @NotNull(message = "Insert transaction type")
    private TransactionType transactionType;

    @NotNull (message = "Enter a valid amount")
    private Double transactionAmount;

    private Double accountBalance;

    private AccountDTO account;

    private String name;


}
