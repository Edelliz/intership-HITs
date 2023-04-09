package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.VacancyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyEntity, Integer> {

    List<VacancyEntity> findByCompanyId(Integer companyId);
}
