package fleetoverview.repository;

import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.domain.projection.company.CompanyFilesProjection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer>, JpaSpecificationExecutor<CompanyEntity> {
    List<CompanyEntity> findAllByStatus(CompanyStatusEnum companyStatusEnum);

    @Query(value = """
            select t.name as name,
                   tf.*
            from companies t
                     left join (select cf.company_id,
                                       min(case cf.type when 'INS_CERT' then cf.expiration_date end)     as insuranceCertExp,
                                       min(case cf.type when 'IFTA_LICENSE' then cf.expiration_date end) as iftaExp,
                                       min(case cf.type when 'UCR' then cf.expiration_date end)          as ucrExp,
                                       min(case cf.type when 'CT_PERMIT' then cf.expiration_date end)    as permitExp,
                                       min(case cf.type when 'MCS_150' then cf.expiration_date end)      as mcsExp
                                from company_files cf
                                         left join companies t on cf.company_id = t.id
                                where t.id = :companyId
                                  and t.status = 'ACTIVE'
                                  and cf.status = 'ACTIVE'
                                  and cf.expiration_date >= CURRENT_DATE
                                group by cf.company_id) tf on tf.company_id = t.id
            where t.id = :companyId
              and t.status = 'ACTIVE'
            order by t.id
            """, nativeQuery = true)
    List<CompanyFilesProjection> getCompaniesWithExpirationInfo(int companyId);

}
