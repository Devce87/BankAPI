package backend.bankaccount.web.app.controller;

import backend.bankaccount.web.app.domain.dto.DateRange;
import backend.bankaccount.web.app.domain.dto.TransactionDTO;
import backend.bankaccount.web.app.service.repo.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;

    }

    @GetMapping("/{id}/client")
    public List<TransactionDTO> getReportByClientByDate(@PathVariable long id, @RequestBody DateRange dateRange){

        return reportService.getBankStatement(id, dateRange.getDate1(), dateRange.getDate2());

    }
}
