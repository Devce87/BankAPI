package backend.bankaccount.web.app.mapper;

import backend.bankaccount.web.app.domain.dto.AccountDTO;
import backend.bankaccount.web.app.domain.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements DefaultMapper<Account, AccountDTO>{

    @Override
    public AccountDTO entityToDTO(Account account) {
        return AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .initialAmount(account.getInitialAmount())
                .balance(account.getBalance())
                .status(account.getStatus())
                .build();
    }

    @Override
    public Account DTOtoEntity(AccountDTO accountDTO) {
        return Account.builder()
                .accountNumber(accountDTO.getAccountNumber())
                .accountType(accountDTO.getAccountType())
                .initialAmount(accountDTO.getInitialAmount())
                .balance(accountDTO.getBalance())
                .status(accountDTO.getStatus())
                .build();
    }
}
