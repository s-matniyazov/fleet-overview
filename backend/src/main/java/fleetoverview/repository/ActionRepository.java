package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.ActionEntity;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 янв. 2025
 **/
public interface ActionRepository extends JpaRepository<ActionEntity, Integer> {
    boolean existsByUrl(String url);
}
