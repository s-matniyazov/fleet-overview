package fleetoverview.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 25 март 2025
 **/
@Component
public class EntityManagerProvider {

    private static EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        entityManager = em;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}