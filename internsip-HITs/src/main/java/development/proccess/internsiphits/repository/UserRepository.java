package development.proccess.internsiphits.repository;

import development.proccess.internsiphits.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
