package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT NAME FROM COMPANY WHERE ID = :id")
    String getCompanyNameById(Integer id);
}
