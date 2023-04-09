package development.proccess.internsiphits.domain.dto;

import lombok.Data;

@Data
public class CreateVacancyDto {
    private String name;

    private String requirements;

    private String description;
}
