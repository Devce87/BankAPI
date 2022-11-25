package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.mapper.TransactionMapper;
import backend.bankaccount.web.app.service.repo.ReportService;
import backend.bankaccount.web.app.service.repo.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {


    private final TransactionService<TransactionDTO> transactionService;

    private final TransactionMapper transactionMapper;

    public ReportServiceImpl(TransactionService<TransactionDTO> transactionService, TransactionMapper transactionMapper) {
        this.transactionService = transactionService;

        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionDTO> getBankStatement(long id, String initialDate, String finalDate) {

//
        LocalDate firstDate = LocalDate.parse(initialDate);
        LocalDate secondDate = LocalDate.parse(finalDate);

        return transactionService.getAllTransactionsByClient(id)
                .stream()
                .filter
                        (tDate ->
                                LocalDate.parse(tDate.getDate()).isAfter(firstDate)
                                        && LocalDate.parse(tDate.getDate()).isBefore(secondDate))
                .collect(Collectors.toList());

    }
}
