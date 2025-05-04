package fleetoverview.service.impl;


import fleetoverview.data.request.StateRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
<<<<<<< HEAD:backend/src/main/java/fleetoverview/service/impl/StateServiceImpl.java
import fleetoverview.domain.entity.StateEntity;
=======
import fleetoverview.domain.entity.CityEntity;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.repository.CityRepository;
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49:backend/src/main/java/fleetoverview/service/impl/CityServiceImpl.java
import fleetoverview.repository.CountryRepository;
import fleetoverview.repository.StateRepository;

import fleetoverview.service.StateService;
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
public class StateServiceImpl extends BaseService implements StateService {
    private final StateRepository repository;
    private final CountryRepository countryRepository;

    @Autowired
<<<<<<< HEAD:backend/src/main/java/fleetoverview/service/impl/StateServiceImpl.java
    public StateServiceImpl(StateRepository repository, CountryRepository countryRepository) {
=======
    private EntityManager entityManager;

    @Autowired
    public CityServiceImpl(CityRepository repository, CountryRepository countryRepository) {
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49:backend/src/main/java/fleetoverview/service/impl/CityServiceImpl.java
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @Override
<<<<<<< HEAD:backend/src/main/java/fleetoverview/service/impl/StateServiceImpl.java
    public DataResponse<List<StateEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll());
=======
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
>>>>>>> 55023a1902470c26e5dd14d3e4e6ebd007716a49:backend/src/main/java/fleetoverview/service/impl/CityServiceImpl.java
    }

    @Override
    public ApiResponse post(StateRequest data) {
        repository.findByNameAndState_Id(data.name(), data.countryId()).ifPresent((city) -> {
            throw new ExistsException(mSourceBundle.apply("city.not_found"));
        });

        repository.save(
                new StateEntity(
                        data.name(),
                        countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")))
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(StateRequest data) {
        StateEntity state = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found")));

        state.setName(data.name());
        state.setCountry(countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(StateRequest data) {
        StateEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        repository.delete(city);

        return ApiResponse.success();
    }
}
