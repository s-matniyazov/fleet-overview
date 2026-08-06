package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);

    @Query("SELECT DISTINCT r FROM RoleEntity r LEFT JOIN FETCH r.roleActions WHERE r.id = :id")
    RoleEntity findRoleWithActions(@Param("id") Integer id);
}
