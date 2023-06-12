package development.proccess.internsiphits.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferDto {
    private Integer id;
    private Integer priority;
    private String companyName;
    private UserShortDto student;
}
