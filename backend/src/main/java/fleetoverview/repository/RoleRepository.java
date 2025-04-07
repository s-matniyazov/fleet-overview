package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 янв. 2025
 **/
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);

    @Query("SELECT DISTINCT r FROM RoleEntity r LEFT JOIN FETCH r.roleActions WHERE r.id = :id")
    RoleEntity findRoleWithActions(@Param("id") Integer id);
}
