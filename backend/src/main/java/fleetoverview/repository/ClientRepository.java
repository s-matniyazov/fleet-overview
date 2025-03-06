package fleetoverview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import fleetoverview.domain.entity.ClientEntity;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
public interface ClientRepository extends JpaRepository<ClientEntity, Integer> {
    boolean existsByName(String name);
}
