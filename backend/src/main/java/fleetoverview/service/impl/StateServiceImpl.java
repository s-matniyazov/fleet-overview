package fleetoverview.service.impl;


import fleetoverview.data.request.StateRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.repository.StateRepository;
import fleetoverview.repository.CountryRepository;
import fleetoverview.repository.StateRepository;

import fleetoverview.service.StateService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import fleetoverview.util.exceptions.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private EntityManager entityManager;

    @Autowired
    public StateServiceImpl(StateRepository repository, CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResponse<List<StateEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<StateEntity> cq = cb.createQuery(StateEntity.class);
        Root<StateEntity> states = cq.from(StateEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("countryId")) {
            Join<StateEntity, CountryEntity> country = states.join("country");
            filters.add(cb.equal(country.get("id"), params.get("countryId")));
        }

        cq.select(states)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(states.get("id")));

        TypedQuery<StateEntity> query = entityManager.createQuery(cq);
        List<StateEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public ApiResponse post(StateRequest data) {
        repository.findByNameAndId(data.name(), data.countryId()).ifPresent((state) -> {
            throw new ExistsException(mSourceBundle.apply("state.not_found"));
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
        StateEntity state = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found")));

        repository.delete(state);

        return ApiResponse.success();
    }
}
