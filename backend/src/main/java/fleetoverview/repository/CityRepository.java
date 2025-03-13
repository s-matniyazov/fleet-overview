package fleetoverview.repository;

import fleetoverview.domain.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
}
