package development.proccess.internsiphits.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacancyDto {

    private Integer id;

    private String name;

    private String requirements;

    private String description;

    private Integer companyId;

    private String companyName;
}
