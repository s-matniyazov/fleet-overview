package fleetoverview.repository;

import fleetoverview.domain.entity.trailer.TrailerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public interface TrailerTypeRepository extends JpaRepository<TrailerTypeEntity, Integer> {
    boolean existsByName(String name);
}
