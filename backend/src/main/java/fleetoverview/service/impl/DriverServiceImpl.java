package fleetoverview.service.impl;

import fleetoverview.data.request.DriverFileRequest;
import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.entity.driver.DriverFileEntity;
import fleetoverview.domain.entity.driver.EndorsementEntity;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
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
    private final TruckRepository truckRepository;
    private final ResourceService resourceService;
    private final EndorsementRepository endorsementRepository;
    private final DriverFileRepository driverFileRepository;

    @Autowired
    private EntityManager entityManager;

    private final SqlSessionFactory db;

    @Autowired
    public DriverServiceImpl(DriverRepository repository, StateRepository stateRepository, CompanyRepository companyRepository, TruckRepository truckRepository, ResourceService resourceService, EndorsementRepository endorsementRepository, DriverFileRepository driverFileRepository, SqlSessionFactory db) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.companyRepository = companyRepository;
        this.truckRepository = truckRepository;
        this.resourceService = resourceService;
        this.endorsementRepository = endorsementRepository;
        this.driverFileRepository = driverFileRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<DriverEntity>> get(Map<String, String> params) {
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
                        companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))),
                        data.firstName(),
                        data.lastName(),
                        data.middleName(),
                        data.hireDate(),
                        data.terminationDate(),
                        data.dateOfBirth(),
                        stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))),
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
        DriverEntity driver = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        driver.setCompany(companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));
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
        driver.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));
        driver.setStatus(data.status());
        driver.setType(data.type());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(DriverRequest data) {
        // not implemented yet

        return ApiResponse.success();
    }

    @Override
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
            driverFile.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));
            driverFile.setClassType(data.classType());
            driverFile.setIssuedDate(data.issuedDate());
            driverFile.setEndorsement(
                    data.endorsement().stream()
                            .map(it -> endorsementRepository.findById(it.id())
                                    .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("endorsement.not_found"))))
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
        List<DriverFileEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public DataResponse<List<EndorsementEntity>> getEndorsements() {
        return DataResponse.success(endorsementRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

}
