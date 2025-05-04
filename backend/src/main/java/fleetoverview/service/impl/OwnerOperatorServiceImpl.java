package fleetoverview.service.impl;

import fleetoverview.data.request.OwnerOperatorRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.OwnerOperatorEntity;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.OwnerOperatorRepository;
import fleetoverview.service.OwnerOperatorService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class OwnerOperatorServiceImpl extends BaseService implements OwnerOperatorService {
    private final OwnerOperatorRepository repository;
    private final CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public OwnerOperatorServiceImpl(OwnerOperatorRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    @Override
    public DataResponse<List<OwnerOperatorEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OwnerOperatorEntity> cq = cb.createQuery(OwnerOperatorEntity.class);
        Root<OwnerOperatorEntity> ownerOperators = cq.from(OwnerOperatorEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("companyId")) {
            Join<OwnerOperatorEntity, CompanyEntity> company = ownerOperators.join("company");
            filters.add(cb.equal(company.get("id"), params.get("companyId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        cq.select(ownerOperators)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(ownerOperators.get("id")));

        TypedQuery<OwnerOperatorEntity> query = entityManager.createQuery(cq);
        List<OwnerOperatorEntity> results = query.getResultList();

        return DataResponse.success(results);

//        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OwnerOperatorRequest data) {
        repository.save(
                new OwnerOperatorEntity(
                        data.name(),
                        companyRepository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found")))
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OwnerOperatorRequest data) {
        OwnerOperatorEntity ownerOperator = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found")));

        ownerOperator.setName(data.name());
        ownerOperator.setCompany(companyRepository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OwnerOperatorRequest data) {
        OwnerOperatorEntity ownerOperator = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found")));

        repository.delete(ownerOperator);

        return ApiResponse.success();
    }
}
