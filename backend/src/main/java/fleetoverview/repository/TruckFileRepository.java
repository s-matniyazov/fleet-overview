package fleetoverview.repository;

import fleetoverview.domain.entity.truck.TruckFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
public interface TruckFileRepository extends JpaRepository<TruckFileEntity, Integer> {
    @Query(value = "select file from TruckFileEntity file where file.expirationDate < CURRENT_DATE and file.status = 'ACTIVE'")
    List<TruckFileEntity> findExpiredFiles();
}
