package development.proccess.internsiphits.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOfferDto {
    private Integer companyId;
    private Integer vacancyId;
    private Integer userId;
}
