package fleetoverview.repository;

import fleetoverview.domain.entity.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
public interface TruckRepository extends JpaRepository<TruckEntity, Integer> {
}
