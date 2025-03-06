package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.RoleEntity;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 янв. 2025
 **/
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);
}
