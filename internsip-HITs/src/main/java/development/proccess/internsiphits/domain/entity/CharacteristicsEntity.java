package development.proccess.internsiphits.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "CHARACTERISTICS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CharacteristicsEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer userId;

    private String content;
}
