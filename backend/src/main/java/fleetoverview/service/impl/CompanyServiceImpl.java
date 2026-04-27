package fleetoverview.service.impl;

import fleetoverview.data.request.CompanyFileRequest;
import fleetoverview.data.request.CompanyRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.company.CompanyFileEntity;
import fleetoverview.domain.enums.company.CompanyFileStatusEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.domain.projection.company.CompanyProjection;
import fleetoverview.repository.CompanyFileRepository;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.StateRepository;
import fleetoverview.service.CompanyService;
import fleetoverview.service.ResourceService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {
    private final CompanyRepository repository;
    private final StateRepository stateRepository;
    private final ResourceService resourceService;
    private final CompanyFileRepository companyFileRepository;

    @Autowired
    private EntityManager entityManager;

    private final SqlSessionFactory db;

    @Autowired
    public CompanyServiceImpl(CompanyRepository repository, StateRepository stateRepository, ResourceService resourceService, CompanyFileRepository companyFileRepository, SqlSessionFactory db) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.resourceService = resourceService;
        this.companyFileRepository = companyFileRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<CompanyEntity>> get(Map<String, Object> params) {
        Integer userId = Integer.valueOf(params.get("userId").toString());

        return DataResponse.success(
                repository.findByCreatedById(
                        userId,
                        Sort.by(Sort.Direction.DESC, "id")
                )
        );
    }

    @Override
    public DataResponse<List<CompanyProjection>> getWithFiles(Map<String, String> params) {
        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("selectCompanies", params)
            );
        }
    }

    @Override
    public ApiResponse deactivate(Integer id) {
        CompanyEntity company = repository.findById(id).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not.found")));
        company.setStatus(CompanyStatusEnum.INACTIVE);
        repository.save(company);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse activate(Integer id) {
        CompanyEntity company = repository.findById(id).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not.found")));
        company.setStatus(CompanyStatusEnum.ACTIVE);
        repository.save(company);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse post(CompanyRequest data) {
        repository.save(
                new CompanyEntity(
                        data.name(),
                        data.ownerName(),
                        data.dot(),
                        data.pinNumber(),
                        data.mc(),
                        data.dba(),
                        data.fein(),
                        stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))),
                        data.address1(),
                        data.address2(),
                        data.city(),
                        data.zipcode(),
                        data.email(),
                        data.phone(),
                        data.timeZone(),
                        data.status(),
                        data.entrantStatus()
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(CompanyRequest data) {
        CompanyEntity company = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not.found")));

        company.setName(data.name());
        company.setOwnerName(data.ownerName());
        company.setDot(data.dot());
        company.setPinNumber(data.pinNumber());
        company.setMc(data.mc());
        company.setDba(data.dba());
        company.setFein(data.fein());
        company.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));
        company.setAddress1(data.address1());
        company.setAddress2(data.address2());
        company.setCity(data.city());
        company.setZipcode(data.zipcode());
        company.setEmail(data.email());
        company.setPhone(data.phone());
        company.setTimeZone(data.timeZone());
        company.setStatus(data.status());
        company.setEntrantStatus(data.entrantStatus());

        repository.save(company);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(CompanyRequest data) {
        // not implemented
        return null;
    }

    @Override
    public ApiResponse attachFile(CompanyFileRequest data, MultipartFile file) {
        CompanyEntity company = repository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found")));

        ResourceEntity resource = resourceService.createResource(file, "company");

        companyFileRepository.save(
                new CompanyFileEntity(
                        resource,
                        data.expirationDate(),
                        data.filedPeriod(),
                        data.nextUpdateDate(),
                        data.description(),
                        data.type(),
                        CompanyFileStatusEnum.ACTIVE,
                        company
                )
        );

        return ApiResponse.success();
    }

    @Override
    public DataResponse<List<CompanyFileEntity>> getFiles(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CompanyFileEntity> cq = cb.createQuery(CompanyFileEntity.class);
        Root<CompanyFileEntity> companyFiles = cq.from(CompanyFileEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("companyId")) {
            Join<CompanyFileEntity, CompanyEntity> company = companyFiles.join("company");
            filters.add(cb.equal(company.get("id"), params.get("companyId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        cq.select(companyFiles)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(companyFiles.get("id")));

        TypedQuery<CompanyFileEntity> query = entityManager.createQuery(cq);
        List<CompanyFileEntity> results = query.getResultList();

        return DataResponse.success(results);
    }
}
