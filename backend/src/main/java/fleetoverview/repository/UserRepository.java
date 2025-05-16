package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.UserEntity;

import java.util.Optional;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);

}
