package fleetoverview.repository;

import fleetoverview.domain.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
}
