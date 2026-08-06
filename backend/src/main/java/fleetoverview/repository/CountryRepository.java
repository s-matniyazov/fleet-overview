package fleetoverview.repository;

import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.domain.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    Optional<CountryEntity> findByName(String name);
}
