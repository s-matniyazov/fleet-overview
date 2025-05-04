package fleetoverview.repository;

import fleetoverview.domain.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
public interface StateRepository extends JpaRepository<StateEntity, Integer> {
    Optional<StateEntity> findByNameAndId(String name, int stateId);
}
