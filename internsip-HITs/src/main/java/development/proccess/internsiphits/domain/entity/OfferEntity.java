package development.proccess.internsiphits.domain.entity;
import development.proccess.internsiphits.domain.entity.enums.OfferPriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFFER")
public class OfferEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY)
    private VacancyEntity vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity student;
}
