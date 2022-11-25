package backend.bankaccount.web.app.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DateRangeDTO {

    String date1;
    String date2;
}
