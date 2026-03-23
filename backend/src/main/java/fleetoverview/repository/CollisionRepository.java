package fleetoverview.repository;

import fleetoverview.domain.entity.claim.CollisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollisionRepository extends JpaRepository<CollisionEntity, Integer> {
}
