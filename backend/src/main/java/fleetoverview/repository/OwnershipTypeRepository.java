package fleetoverview.repository;

import fleetoverview.domain.entity.OwnershipTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnershipTypeRepository extends JpaRepository<OwnershipTypeEntity, Integer> {
    boolean existsByName(String company);
}
