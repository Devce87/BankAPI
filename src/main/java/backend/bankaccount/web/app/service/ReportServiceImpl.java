package backend.bankaccount.web.app.service;

import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.exception.NoTransactionsForDateRange;

import backend.bankaccount.web.app.service.contract.ReportService;
import backend.bankaccount.web.app.service.contract.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final TransactionService<TransactionDTO> transactionService;


    public ReportServiceImpl(TransactionService<TransactionDTO> transactionService) {
        this.transactionService = transactionService;

    }
    @Override
    public List<TransactionDTO> getBankStatement(long id, String initialDate, String finalDate) {
        log.info("Looking for Transactions from: {} to: {}", initialDate,finalDate);

        if(transactionsForRange(id,initialDate,finalDate).isEmpty())
            throw new NoTransactionsForDateRange("No transactions could be retrieved for specified dates");

        return (transactionsForRange(id, initialDate, finalDate));
    }


    private List<TransactionDTO> transactionsForRange(long id, String  fromDate, String toDate) {


        LocalDate firstDate = LocalDate.parse(fromDate);
        LocalDate secondDate = LocalDate.parse(toDate);

        if(fromDate.equals(toDate)){
            return transactionService.getAllTransactionsByClient(id)
                    .stream()
                    .filter(tDate -> LocalDate.parse(tDate.getDate()).equals(firstDate)).collect(Collectors.toList());

        }
        return transactionService.getAllTransactionsByClient(id)
                .stream()
                .filter(tDate ->
                        LocalDate.parse(tDate.getDate()).isAfter(firstDate)
                                && LocalDate.parse(tDate.getDate()).isBefore(secondDate))
                .collect(Collectors.toList());
    }
}
