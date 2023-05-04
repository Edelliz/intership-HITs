package development.proccess.internsiphits.domain.dto;

import development.proccess.internsiphits.domain.entity.enums.Mark;
import development.proccess.internsiphits.domain.entity.enums.ReportStatus;
import lombok.Data;

@Data
public class UpdateReportDto {

    private Mark mark;

    private ReportStatus status;
}
