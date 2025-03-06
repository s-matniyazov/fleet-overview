package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.MenuEntity;

/**
 * @author :  qobiljon.qodirjonov
 * @mailto :  1810092kk@gmail.com
 * @created : 28 янв. 2025
 **/
public interface MenuRepository extends JpaRepository<MenuEntity, Integer>  {
}
