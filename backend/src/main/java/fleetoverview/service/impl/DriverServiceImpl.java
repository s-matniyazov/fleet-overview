package fleetoverview.service.impl;

import fleetoverview.data.request.DriverFileRequest;
import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.request.TerminationRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.entity.driver.DriverFileEntity;
import fleetoverview.domain.entity.driver.EndorsementEntity;
import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.domain.enums.driver.DriverFileStatusEnum;
import fleetoverview.domain.enums.driver.DriverFileTypeEnum;
import fleetoverview.repository.*;
import fleetoverview.service.DriverService;
import fleetoverview.service.ResourceService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl extends BaseService implements DriverService {
    private final DriverRepository repository;
    private final StateRepository stateRepository;
    private final CompanyRepository companyRepository;
    private final ResourceService resourceService;
    private final EndorsementRepository endorsementRepository;
    private final DriverFileRepository driverFileRepository;

    @Autowired
    private EntityManager entityManager;

    private final SqlSessionFactory db;

    @Autowired
    public DriverServiceImpl(DriverRepository repository, StateRepository stateRepository,
                             CompanyRepository companyRepository, ResourceService resourceService,
                             EndorsementRepository endorsementRepository, DriverFileRepository driverFileRepository, SqlSessionFactory db) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.companyRepository = companyRepository;
        this.resourceService = resourceService;
        this.endorsementRepository = endorsementRepository;
        this.driverFileRepository = driverFileRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<DriverEntity>> get(Map<String, Object> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("selectDrivers", params)
            );
        }
    }

    @Override
    public ApiResponse post(DriverRequest data) {
        repository.save(
                new DriverEntity(
                        companyRepository.getReferenceById(data.companyId()),
                        data.firstName(),
                        data.lastName(),
                        data.middleName(),
                        data.hireDate(),
                        data.terminationDate(),
                        data.dateOfBirth(),
                        stateRepository.getReferenceById(data.stateId()),
                        data.address(),
                        data.city(),
                        data.zipCode(),
                        data.email(),
                        data.phone(),
                        data.status(),
                        data.type()
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(DriverRequest data) {
        DriverEntity driver = repository.findById(data.id()).orElseThrow(()-> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        driver.setCompany(companyRepository.getReferenceById(data.companyId()));
        driver.setFirstName(data.firstName());
        driver.setMiddleName(data.middleName());
        driver.setLastName(data.lastName());
        driver.setAddress(data.address());
        driver.setCity(data.city());
        driver.setEmail(data.email());
        driver.setPhone(data.phone());
        driver.setZipCode(data.zipCode());
        driver.setHireDate(data.hireDate());
        driver.setTerminationDate(data.terminationDate());
        driver.setDateOfBirth(data.dateOfBirth());
        driver.setState(stateRepository.getReferenceById(data.stateId()));
        driver.setStatus(data.status());
        driver.setType(data.type());

        repository.save(driver);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(DriverRequest data) {
        // not implemented yet

        return ApiResponse.success();
    }

    @Override
    public ApiResponse terminate(TerminationRequest data) {
        // not implemented yet
        DriverEntity driver = repository.findById(data.driverId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        if (driver.getStatus().equals(DriverStatusEnum.ACTIVE)) {
            driver.setTerminationDate(LocalDateTime.now());
            driver.setTerminationReason(data.reason());
            driver.setStatus(DriverStatusEnum.PASSIVE);
        } else {
            driver.setTerminationDate(null);
            driver.setTerminationReason("");
            driver.setStatus(DriverStatusEnum.ACTIVE);
        }
        repository.save(driver);
        return ApiResponse.success();
    }

    @Override
    @Transactional
    public ApiResponse attachFile(DriverFileRequest data, MultipartFile file) {
        DriverEntity driver = repository.findById(data.driverId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        ResourceEntity resource = resourceService.createResource(file, "driver");

        DriverFileEntity driverFile = new DriverFileEntity(
                resource,
                data.expirationDate(),
                data.description(),
                data.type(),
                DriverFileStatusEnum.ACTIVE,
                driver
        );

        if (data.type().equals(DriverFileTypeEnum.CDL)) {
            driverFile.setDriversLicense(data.driversLicense());
            driverFile.setState(stateRepository.getReferenceById(data.stateId()));
            driverFile.setClassType(data.classType());
            driverFile.setIssuedDate(data.issuedDate());
            driverFile.setEndorsement(
                    data.endorsement().stream()
                            .map(it -> endorsementRepository.getReferenceById(it.id()))
                            .collect(Collectors.toList())
            );
        } else if (data.type().equals(DriverFileTypeEnum.MEDICAL_CERT) || data.type().equals(DriverFileTypeEnum.CLEARING_HOUSE)) {
            driverFile.setLastCollectedOn(data.lastCollectedOn());
        } else if (data.type().equals(DriverFileTypeEnum.SSN)) {
            driverFile.setSocialSecurityNumber(data.socialSecurityNumber());
        }

        driverFileRepository.save(driverFile);

        return ApiResponse.success();
    }

    @Override
    public DataResponse<List<DriverFileEntity>> getFiles(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DriverFileEntity> cq = cb.createQuery(DriverFileEntity.class);
        Root<DriverFileEntity> driverFiles = cq.from(DriverFileEntity.class);

        driverFiles.fetch("endorsement", JoinType.LEFT);
        driverFiles.fetch("resource", JoinType.LEFT);
        driverFiles.fetch("state", JoinType.LEFT);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("driverId")) {
            Join<DriverFileEntity, DriverEntity> driver = driverFiles.join("driver");
            filters.add(cb.equal(driver.get("id"), params.get("driverId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.driver.missed"));
        }

        cq.select(driverFiles)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(driverFiles.get("id")));

        TypedQuery<DriverFileEntity> query = entityManager.createQuery(cq);
        List<DriverFileEntity> results = query.getResultList().stream().distinct().toList();

        return DataResponse.success(results);
    }

    @Override
    public DataResponse<List<EndorsementEntity>> getEndorsements() {
        return DataResponse.success(endorsementRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

}
