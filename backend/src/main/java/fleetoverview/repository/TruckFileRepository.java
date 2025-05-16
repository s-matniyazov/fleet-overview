package fleetoverview.repository;

import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.TruckFileEntity;
import fleetoverview.domain.enums.TruckFileStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 14 май 2025
 **/
public interface TruckFileRepository extends JpaRepository<TruckFileEntity, Integer> {
    List<TruckFileEntity> findAllByTruck_CompanyAndExpirationDateLessThanAndStatus(CompanyEntity truck_company, Date expirationDate, TruckFileStatusEnum status);
}
