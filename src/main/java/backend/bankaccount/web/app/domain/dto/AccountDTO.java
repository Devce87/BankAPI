package backend.bankaccount.web.app.domain.dto;

import backend.bankaccount.web.app.constants.AccountType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDTO {
    private String accountNumber;
    private AccountType accountType;
    private Double initialAmount;
    private Double balance;
    private String status;
}
