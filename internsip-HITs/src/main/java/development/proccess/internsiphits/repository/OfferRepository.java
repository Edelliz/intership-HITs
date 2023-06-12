package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.OfferEntity;
import development.proccess.internsiphits.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<OfferEntity, Integer> {
    List<OfferEntity> findAllByStudent(UserEntity student);
}
