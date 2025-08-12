package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.projection.driver.ExpirationDriverFilesProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
    @Query("select count(*) from TruckEntity tr where tr.driver.id = :truckId")
    int hasTruckById(int truckId);

    @Query(value = """
            select t.first_name || ' ' || t.last_name as driverName,
                   df.*
            from drivers t
                     left join (select df.driver_id,
                                       min(case df.type when 'CDL' then df.expiration_date end)                 as cdlExp,
                                       min(case df.type when 'MEDICAL_CERT' then df.expiration_date end)        as medicalCertExp,
                                       min(case df.type when 'MVR' then df.expiration_date end)                 as mvrExp,
                                       min(case df.type when 'CLEARING_HOUSE' then df.expiration_date end)      as clearingHouseExp,
                                       min(case df.type when 'SSN' then df.expiration_date end)                 as ssnExp,
                                       min(case df.type when 'CCF' then df.expiration_date end)                 as ccfExp,
                                       min(case df.type when 'DRUG_TEST_RESULT' then df.expiration_date end)    as drugTestResultExp,
                                       min(case df.type when 'DRIVER_APPLICATION' then df.expiration_date end)  as driverApplicationExp,
                                       min(case df.type when 'PEV' then df.expiration_date end)                 as pevExp
                                from public.driver_files df
                                         left join trailers t on df.driver_id = t.id
                                where t.company_id = :companyId
                                  and df.status = 'ACTIVE'
                                  and df.expiration_date >= CURRENT_DATE
                                group by df.driver_id) df on df.driver_id = t.id
            where t.company_id = :companyId
            order by t.id""", nativeQuery = true)
    List<ExpirationDriverFilesProjection> getDriversWithExpirationInfo(Integer companyId);
}
