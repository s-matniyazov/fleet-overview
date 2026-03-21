package fleetoverview.repository;

import fleetoverview.domain.entity.inspection.ViolationTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViolationTypeRepository extends JpaRepository<ViolationTypes, Integer> {
    @Modifying
    @Query("DELETE FROM ViolationTypes v WHERE v.inspection.id = :inspectionId")
    void deleteAllByInspectionId(int inspectionId);


    List<ViolationTypes> findByInspectionId(Integer inspectionId);

}