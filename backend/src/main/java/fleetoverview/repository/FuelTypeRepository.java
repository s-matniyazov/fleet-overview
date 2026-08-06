package fleetoverview.repository;

import fleetoverview.domain.entity.truck.FuelTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FuelTypeRepository extends JpaRepository<FuelTypeEntity, Integer> {
}
