package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

    ReportEntity findByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}
