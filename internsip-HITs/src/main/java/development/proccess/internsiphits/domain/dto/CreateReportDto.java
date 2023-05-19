package development.proccess.internsiphits.domain.dto;

import development.proccess.internsiphits.domain.entity.enums.SupervisorName;
import lombok.Data;

@Data
public class CreateReportDto {

    private SupervisorName supervisorName;
    private String studentCharacteristic;
}
