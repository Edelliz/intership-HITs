package development.proccess.internsiphits.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "VACANCY")
public class VacancyEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String requirements;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

}
