package backend.bankaccount.web.app.domain.repo;

import backend.bankaccount.web.app.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
