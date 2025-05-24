package fleetoverview.repository;

import fleetoverview.domain.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
}
