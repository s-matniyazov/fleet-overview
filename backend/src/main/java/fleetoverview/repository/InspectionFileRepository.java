package fleetoverview.repository;

import fleetoverview.domain.entity.inspection.InspectionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InspectionFileRepository extends JpaRepository<InspectionFile, Integer> {

    List<InspectionFile> findAllByInspectionIdOrderByIdDesc(int inspectionId);
}
