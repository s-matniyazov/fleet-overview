package fleetoverview.repository;

import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public interface TrailerRepository extends JpaRepository<TrailerEntity, Integer> {
    @Override
    @Query("""
            select t from TrailerEntity t
            left join fetch TrailerFileEntity tf on tf.trailer = t and tf.status = 'ACTIVE'
            left join fetch PermitEntity tp on tp.trailer = t and tp.status = 'ACTIVE'
            """)
    List<TrailerEntity> findAll();
}
