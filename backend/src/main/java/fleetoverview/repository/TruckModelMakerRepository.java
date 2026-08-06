package fleetoverview.repository;

import fleetoverview.domain.entity.truck.TruckModelMakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TruckModelMakerRepository extends JpaRepository<TruckModelMakerEntity, Integer> {
    boolean existsByName(String name);
}
