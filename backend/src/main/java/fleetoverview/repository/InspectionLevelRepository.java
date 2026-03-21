package fleetoverview.repository;

import fleetoverview.domain.entity.inspection.InspectionLevelsRef;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InspectionLevelRepository extends JpaRepository<InspectionLevelsRef, Integer> {
}
