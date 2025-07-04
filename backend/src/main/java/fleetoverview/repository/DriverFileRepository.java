package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 июль 2025
 **/
public interface DriverFileRepository extends JpaRepository<DriverFileEntity, Integer> {
}
