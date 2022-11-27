package backend.bankaccount.web.app.service.contract;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;

import java.util.List;

public interface ReportService {

    List<TransactionDTO> getBankStatement(long id, String initialDate, String finalDate);

}
