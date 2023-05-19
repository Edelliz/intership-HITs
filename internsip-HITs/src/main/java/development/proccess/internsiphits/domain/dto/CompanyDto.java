package development.proccess.internsiphits.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDto {

    private Integer id;

    private String name;

    private String fullName;

    private String info;

    private List<VacancyDto> vacancies;
}
