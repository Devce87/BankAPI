package backend.bankaccount.web.app.service.repo;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;

import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.List;

public interface ReportService {

    List<TransactionDTO> getBankStatement(long id, String initialDate, String finalDate);

}
