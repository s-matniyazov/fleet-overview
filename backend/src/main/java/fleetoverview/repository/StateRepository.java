package fleetoverview.repository;

import fleetoverview.domain.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StateRepository extends JpaRepository<StateEntity, Integer> {
    Optional<StateEntity> findByNameAndId(String name, int stateId);
}
