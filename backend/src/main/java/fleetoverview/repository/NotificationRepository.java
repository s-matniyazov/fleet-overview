package fleetoverview.repository;

import fleetoverview.domain.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}
