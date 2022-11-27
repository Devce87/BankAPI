package backend.bankaccount.web.app.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class DateRangeDTO {

    @NotBlank(message = "Insert 'from' date")
    String date1;

    @NotBlank(message = "Insert 'to' date")
    String date2;
}
