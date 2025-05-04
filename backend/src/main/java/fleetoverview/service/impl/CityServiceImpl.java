package fleetoverview.service.impl;

import fleetoverview.data.request.CityRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CityEntity;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.repository.CityRepository;
import fleetoverview.repository.CountryRepository;
import fleetoverview.service.CityService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import fleetoverview.util.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Service
public class CityServiceImpl extends BaseService implements CityService {
    private final CityRepository repository;
    private final CountryRepository countryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public CityServiceImpl(CityRepository repository, CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResponse<List<CityEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CityEntity> cq = cb.createQuery(CityEntity.class);
        Root<CityEntity> cities = cq.from(CityEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("countryId")) {
            Join<CityEntity, CountryEntity> country = cities.join("country");
            filters.add(cb.equal(country.get("id"), params.get("countryId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.country.missed"));
        }

        cq.select(cities)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(cities.get("id")));

        TypedQuery<CityEntity> query = entityManager.createQuery(cq);
        List<CityEntity> results = query.getResultList();

        return DataResponse.success(results);
//        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(CityRequest data) {
        repository.findByNameAndCountry_Id(data.name(), data.countryId()).ifPresent((city) -> {
            throw new ExistsException(mSourceBundle.apply("city.found"));
        });

        repository.save(
                new CityEntity(
                        data.name(),
                        countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")))
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(CityRequest data) {
        CityEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        city.setName(data.name());
        city.setCountry(countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(CityRequest data) {
        CityEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        repository.delete(city);

        return ApiResponse.success();
    }
}
