package backend.bankaccount.web.app.service;


import backend.bankaccount.web.app.domain.entity.Account;
import backend.bankaccount.web.app.domain.repo.AccountRepository;
import backend.bankaccount.web.app.service.contract.AccountService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Random;



@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountService<Account> accountService;

    @Mock
    private AccountRepository accountRepository;


//    @Test
//    void deleteAccount() {
//
//        doNothing().when(accountRepository).deleteById(anyLong());
//
//        accountService.deleteAccount((long) getRandomInt());
//        verify(accountRepository, times(1).deleteById(AnyLong()));
//        verifyNoMoreInteractions(accountRepository);
//
//    }

    private int getRandomInt() {
        return new Random().ints(1, 10).findFirst().getAsInt();
    }
}