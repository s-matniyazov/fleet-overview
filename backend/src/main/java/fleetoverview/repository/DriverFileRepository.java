package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 июль 2025
 **/
public interface DriverFileRepository extends JpaRepository<DriverFileEntity, Integer> {
    @Query(value = "select file from DriverFileEntity file where file.expirationDate < CURRENT_DATE and file.status = 'ACTIVE' and file.type not in ('SSN', 'CCF', 'DRUG_TEST_RESULT', 'DRIVER_APPLICATION', 'PEV')")
    List<DriverFileEntity> findExpiredFiles();
}
