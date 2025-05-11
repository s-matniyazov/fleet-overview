package fleetoverview.service.impl;

import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.domain.entity.DriverEntity;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.repository.DriverRepository;
import fleetoverview.service.DriverService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class DriverServiceImpl extends BaseService implements DriverService {
    private final DriverRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public DriverServiceImpl(DriverRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<DriverEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DriverEntity> cq = cb.createQuery(DriverEntity.class);
        Root<DriverEntity> drivers = cq.from(DriverEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("companyId")) {
            Join<DriverEntity, CompanyEntity> company = drivers.join("company");
            filters.add(cb.equal(company.get("id"), params.get("companyId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.country.missed"));
        }

        cq.select(drivers)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(drivers.get("id")));

        TypedQuery<DriverEntity> query = entityManager.createQuery(cq);
        List<DriverEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public ApiResponse post(DriverRequest data) {
        repository.save(
                new DriverEntity(

                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(DriverRequest data) {
        return null;
    }

    @Override
    public ApiResponse delete(DriverRequest data) {
        return null;
    }



}
