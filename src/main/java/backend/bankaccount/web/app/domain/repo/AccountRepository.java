package backend.bankaccount.web.app.domain.repo;

import backend.bankaccount.web.app.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository <Account, Long> {
}
