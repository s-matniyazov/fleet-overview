package fleetoverview.repository;

import fleetoverview.domain.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
}
