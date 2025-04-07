package fleetoverview.repository;

import fleetoverview.domain.entity.ResourceEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public interface ResourceRepository extends CrudRepository<ResourceEntity, Integer> {
}
