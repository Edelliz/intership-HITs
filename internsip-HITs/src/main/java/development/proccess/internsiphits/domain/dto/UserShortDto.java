package development.proccess.internsiphits.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserShortDto {
    private Integer id;
    private String fullName;
}
