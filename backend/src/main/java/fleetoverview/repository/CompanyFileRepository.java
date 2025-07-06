package fleetoverview.repository;

import fleetoverview.domain.entity.company.CompanyFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
public interface CompanyFileRepository extends JpaRepository<CompanyFileEntity, Integer> {
}
