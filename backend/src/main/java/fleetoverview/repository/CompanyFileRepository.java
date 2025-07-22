package fleetoverview.repository;

import fleetoverview.domain.entity.company.CompanyFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
public interface CompanyFileRepository extends JpaRepository<CompanyFileEntity, Integer> {
    @Query(value = "select file from CompanyFileEntity file where file.expirationDate < CURRENT_DATE and file.status = 'ACTIVE'")
    List<CompanyFileEntity> findExpiredFiles();
}
