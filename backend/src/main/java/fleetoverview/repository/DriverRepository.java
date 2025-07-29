package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
    @Query("select count(*) from TruckEntity tr where tr.driver.id = :truckId")
    int hasTruckById(int truckId);
}
