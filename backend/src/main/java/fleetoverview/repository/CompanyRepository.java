package fleetoverview.repository;

import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {
    List<CompanyEntity> findAllByStatus(CompanyStatusEnum companyStatusEnum);
}
