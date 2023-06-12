package development.proccess.internsiphits.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicsDto {

    private Integer id;

    private Integer userId;

    private String content;
}
