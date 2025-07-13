package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
}
