package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.service.repo.ReportService;
import backend.bankaccount.web.app.service.repo.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.JapaneseChronology;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {


    private final TransactionService<TransactionDTO> transactionService;


    public ReportServiceImpl(TransactionService<TransactionDTO> transactionService) {
        this.transactionService = transactionService;

    }

    @Override
    public List<TransactionDTO> getBankStatement(long id, String initialDate, String finalDate) {

        ChronoLocalDate firstDate = LocalDate.parse(initialDate);
        ChronoLocalDate secondDate = LocalDate.parse(finalDate);

            return transactionService.getAllTransactionsByClient(id).stream()
                    .filter
                            (tDate-> LocalDate.parse(tDate.getDate()).isAfter(firstDate) && LocalDate.parse(tDate.getDate()).isBefore(secondDate))
                    .sorted().collect(Collectors.toList());

    }
}
