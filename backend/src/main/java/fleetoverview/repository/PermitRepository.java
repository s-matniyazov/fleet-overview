package fleetoverview.repository;

import fleetoverview.domain.entity.PermitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
public interface PermitRepository extends JpaRepository<PermitEntity, Integer> {
    @Query(value = "select file from PermitEntity file where file.expirationDate < CURRENT_DATE and file.status = 'ACTIVE'")
    List<PermitEntity> findExpiredFiles();
}
