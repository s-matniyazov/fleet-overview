package fleetoverview.repository;

import fleetoverview.domain.entity.PurchaseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseTypeRepository extends JpaRepository<PurchaseTypeEntity, Integer> {
    boolean existsByName(String name);
}
