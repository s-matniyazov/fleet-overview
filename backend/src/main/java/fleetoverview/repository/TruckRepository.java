package fleetoverview.repository;

import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.projection.ExpirationTruckFilesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
public interface TruckRepository extends JpaRepository<TruckEntity, Integer> {
    @Override
    @Query("""
            select t from TruckEntity t
            left join fetch TruckFileEntity tf on tf.truck = t and tf.status = 'ACTIVE'
            left join fetch PermitEntity tp on tp.truck = t and tp.status = 'ACTIVE'
            """)
    List<TruckEntity> findAll();

    @Query(value = """
            select t.unit  as truckUnit,
                    t.year as truckYear,
                    mm.name as truckMaker,
                    ft.name as truckFuelType,
                    tf.*
             from trucks t
                      left join public.model_makers mm on mm.id = t.model_maker_id
                      left join public.fuel_types ft on ft.id = t.fuel_type_id
                      left join (select tf.truck_id,
                                        min(case tf.type when 'REG_CAB_CARD' then tf.expiration_date end) as regCabCardExp,
                                        min(case tf.type when 'ANN_INS' then tf.expiration_date end)      as annsInsExp,
                                        min(case tf.type when 'PHYS_DAMAGE' then tf.expiration_date end)  as physDamageExp,
                                        min(case tf.type when 'LEASE_AGR' then tf.expiration_date end)    as leaseAgrExp
                                 from truck_files tf
                                          left join trucks t on tf.truck_id = t.id
                                 where t.company_id = :companyId
                                   and tf.status = 'ACTIVE'
                                   and tf.expiration_date >= CURRENT_DATE
                                 group by tf.truck_id) tf on tf.truck_id = t.id
             where t.company_id = :companyId
             order by t.unit""", nativeQuery = true)
    List<ExpirationTruckFilesProjection> getTrucksWithDocInfo(Integer companyId);
}
