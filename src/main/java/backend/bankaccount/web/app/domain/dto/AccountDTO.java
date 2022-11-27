package backend.bankaccount.web.app.domain.dto;

import backend.bankaccount.web.app.constants.AccountType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class AccountDTO {

    @Size(min=6,max=6, message ="Account Number must be 6 digits long.")
    private String accountNumber;

    @NotNull(message = "Insert account type")
    private AccountType accountType;

    @NotNull(message = "Add an Initial amount")
    private Double initialAmount;

    //Account balance will be the same as initialAmount at account.save
    private Double balance;

    @NotBlank
    private String status;
}
