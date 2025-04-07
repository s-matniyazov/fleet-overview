package fleetoverview.repository;

import fleetoverview.domain.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
public interface CityRepository extends JpaRepository<CityEntity, Integer> {
    Optional<CityEntity> findByNameAndCountry_Id(String name, int countryId);
}
