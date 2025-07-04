package fleetoverview.repository;

import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.projection.ExpirationTrailerFilesProjection;
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
            """)
    List<TrailerEntity> findAll();

    @Query(value = """
            select t.unit  as unit,
                    t.year as "year",
                    mm.name as maker,
                    tf.*
             from trailers t
                      left join public.trailer_model_makers mm on mm.id = t.model_maker_id
                      left join (select tf.trailer_id,
                                        min(case tf.type when 'REG_CAB_CARD' then tf.expiration_date end) as regCabCardExp,
                                        min(case tf.type when 'ANN_INS' then tf.expiration_date end)      as annsInsExp,
                                        min(case tf.type when 'PHYS_DAMAGE' then tf.expiration_date end)  as physDamageExp,
                                        min(case tf.type when 'LEASE_AGR' then tf.expiration_date end)    as leaseAgrExp
                                 from trailer_files tf
                                          left join trailers t on tf.trailer_id = t.id
                                 where t.company_id = :companyId
                                   and tf.status = 'ACTIVE'
                                   and tf.expiration_date >= CURRENT_DATE
                                 group by tf.trailer_id) tf on tf.trailer_id = t.id
             where t.company_id = :companyId
             order by t.unit""", nativeQuery = true)
    List<ExpirationTrailerFilesProjection> getTrailersWithExpirationInfo(Integer companyId);
}
