package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.CharacteristicsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicsRepository extends JpaRepository<CharacteristicsEntity, Integer> {

    boolean existsByUserId(Integer userId);

    CharacteristicsEntity findByUserId(Integer userId);

    List<CharacteristicsEntity> findAllByUserId(Integer userId);

    void deleteByUserId(Integer userId);
}
