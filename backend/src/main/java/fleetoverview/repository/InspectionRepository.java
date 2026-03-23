package fleetoverview.repository;

import fleetoverview.domain.entity.inspection.Inspection;
import fleetoverview.domain.enums.inspection.InspectionFileTypeEnum;
import fleetoverview.domain.projection.inspection.InspectionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InspectionRepository extends JpaRepository<Inspection, Integer> {
    @Query(value = """
             select
                 --inspection
                 i.id                   in_id,
                 i.inspection_number    in_inspection_number,
                 i.inspection_date      in_inspection_date,
                 i.company_id           in_company_id,
                 t.unit                 in_unit,
                 i.description          in_description,
                 i.violation_discovered in_violation_discovered,
                 i.status               in_status,
                 i.state_id             in_state_id,
                 i.location             in_location,
                 i.city                 in_city,
                 i.total_fine_amount    in_total_fine_amount,
                 i.inspection_level     in_inspection_level,
                 i.out_of_service       in_out_of_service,
                 i.truck_id             in_truck_id,
                 --driver
                 d.id                   dr_id,
                 d.first_name           dr_first_name,
                 d.last_name            dr_last_name,
                 i.co_driver_id         dr_co_driver_id,
                 --correction and certification
                 fi_cor.id              fi_cor_id,
                 fi_cor.type            fi_cor_type,
                 fi_cor.status          fi_cor_status,
                 fi_cor.description     fi_cor_description,
                 fi_cer.id              fi_cer_id,
                 fi_cer.type            fi_cer_type,
                 fi_cer.status          fi_cer_status,
                 fi_cer.description     fi_cer_description,
                 r_cor.file_name        cor_file_name,
                 r_cor.size             cor_size,
                 r_cer.file_name        cer_file_name,
                 r_cer.size             cer_size
             from inspection i
                      left join drivers d on i.driver_id = d.id
                      left join trucks t on i.truck_id = t.id
                      left join LATERAL (select f.*
                                         from inspection_files f
                                         where f.inspection_id = i.id
                                           and f.status = 'ACTIVE'
                                           and f.type = 'CORRECTION'
                                         order by f.created desc
                                      limit 1) as fi_cor on true
                      LEFT JOIN LATERAL (SELECT f.*
                                         FROM inspection_files f
                                         WHERE f.inspection_id = i.id
                                           AND f.status = 'ACTIVE'
                                           AND f.type = 'CERTIFICATION'
                                         ORDER BY f.created DESC
                                         LIMIT 1) AS fi_cer ON true
                     left join resources r_cor on r_cor.id = fi_cor.resource_id
                     left join resources r_cer on r_cer.id = fi_cer.resource_id
             where i.company_id = :companyId
               and i.status = :status
               and (:driver IS NULL OR lower(d.first_name || ' ' || d.last_name) LIKE CONCAT('%', lower(:driver), '%'))
            order by i.id
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM inspection i
                    left join drivers d on i.driver_id = d.id
                    WHERE i.company_id = :companyId
                    """,
            nativeQuery = true)
    Page<InspectionDto> findAllByCompanyIdAndDriverNameAndStatus(Integer companyId, String driver, String status, Pageable pageable);


    @Query(value = """
            select i.* from inspection i
            where i.company_id = :companyId
            and i.violation_discovered = true
            and i.deadline_at <= current_date
            and (
                  not exists (select f1.id from inspection_files f1 where f1.inspection_id = i.id and f1.type = 'CORRECTION')
                  or
                  not exists (select f2.id from inspection_files f2 where f2.inspection_id = i.id and f2.type = 'CERTIFICATION')
               )
            """, nativeQuery = true)
    List<Inspection> findInspectionsWithDeadlineAndMissingFiles(int companyId);

}