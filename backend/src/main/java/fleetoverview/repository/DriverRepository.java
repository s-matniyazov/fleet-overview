package fleetoverview.repository;

import fleetoverview.domain.entity.DriverEntity;
import fleetoverview.domain.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
}
