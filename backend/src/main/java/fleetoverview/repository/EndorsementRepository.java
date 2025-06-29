package fleetoverview.repository;

import fleetoverview.domain.entity.driver.EndorsementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public interface EndorsementRepository extends JpaRepository<EndorsementEntity, Integer> {
    boolean existsByName(String name);
}
