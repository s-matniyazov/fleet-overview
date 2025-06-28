package fleetoverview.repository;

import fleetoverview.domain.entity.driver.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, Integer> {
    @Query("""
                    select distinct d from DriverEntity d
                        left join fetch d.truck t
                        left join fetch d.files df
                    where t.company.id = :companyId
                      and (df is null or df.status = 'ACTIVE')
            """)
    List<DriverEntity> findAllByCompanyId(int companyId);
}
