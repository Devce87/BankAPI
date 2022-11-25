package backend.bankaccount.web.app.domain.repo;

import backend.bankaccount.web.app.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository <Client, Long> {
}
