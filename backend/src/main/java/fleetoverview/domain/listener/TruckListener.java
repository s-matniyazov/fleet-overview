package fleetoverview.domain.listener;

import fleetoverview.config.EntityManagerProvider;
import fleetoverview.domain.entity.TruckEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 25 март 2025
 **/
public class TruckListener {
    private static final Logger log = LoggerFactory.getLogger(TruckListener.class);

    @PrePersist
    public void beforeSave(TruckEntity truck) {
        log.info("Before save truck: {}", truck);
    }

    @Transactional
    @PreUpdate
    public void beforeUpdate(TruckEntity truck) {
        EntityManager entityManager = EntityManagerProvider.getEntityManager();
        Object oldEntity = entityManager.find(TruckEntity.class, truck.getId());

    }

    @PreRemove
    public void beforeDelete(TruckEntity truck) {
        log.info("Before delete truck: {}", truck);
    }
}
