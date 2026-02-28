package fleetoverview.service.impl;

import fleetoverview.data.request.PermitRequest;
import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.PermitEntity;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.entity.truck.TruckFileEntity;
import fleetoverview.domain.enums.PermitStatusEnum;
import fleetoverview.domain.enums.truck.TruckFileStatusEnum;
import fleetoverview.domain.enums.truck.TruckStatusEnum;
import fleetoverview.repository.*;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TruckService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import fleetoverview.util.exceptions.NotFoundException;
import jakarta.persistence.criteria.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
@Service
public class TruckServiceImpl extends BaseService implements TruckService {
    private final ResourceService resourceService;
    private final TruckRepository repository;
    private final StateRepository stateRepository;
    private final TruckModelMakerRepository makerRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final TruckFileRepository truckFileRepository;
    private final PermitRepository permitRepository;

    @Autowired
    private EntityManager entityManager;

    private final SqlSessionFactory db;

    @Autowired
    public TruckServiceImpl(ResourceService resourceService, TruckRepository repository, StateRepository stateRepository, TruckModelMakerRepository makerRepository,
                            FuelTypeRepository fuelTypeRepository, OwnershipTypeRepository ownershipTypeRepository,
                            PurchaseTypeRepository purchaseTypeRepository, DriverRepository driverRepository, CompanyRepository companyRepository, TruckFileRepository truckFileRepository, PermitRepository permitRepository, SqlSessionFactory db) {
        this.resourceService = resourceService;
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.makerRepository = makerRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
        this.truckFileRepository = truckFileRepository;
        this.permitRepository = permitRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<TruckEntity>> get(Map<String, Object> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("selectTrucks", params)
            );
        }
    }

    @Override
    public ApiResponse post(TruckRequest data) {
        if (driverRepository.hasTruckById(data.driverId()) != 0)
            throw new ExistsException(mSourceBundle.apply("driver.hasTruck"));

        if (repository.existsByUnit(data.unit()))
            throw new ExistsException(mSourceBundle.apply("truck.unit.taken"));

        repository.save(
                new TruckEntity(
                        data.unit(),
                        data.inServiceDate(),
                        data.licensePlate(),
                        stateRepository.getReferenceById(data.stateId()),
                        makerRepository.getReferenceById(data.modelMakerId()),
                        data.year(),
                        fuelTypeRepository.getReferenceById(data.fuelTypeId()),
                        data.grossWeight(),
                        data.axles(),
                        data.vin(),
                        data.ownershipTypeId() != 0 ? ownershipTypeRepository.getReferenceById(data.ownershipTypeId()) : null,
                        data.includeIFTA(),
                        data.purchaseTypeId() != 0 ? purchaseTypeRepository.getReferenceById(data.purchaseTypeId()) : null,
                        data.driverId() != 0 ? driverRepository.getReferenceById(data.driverId()) : null,
                        data.description(),
                        companyRepository.getReferenceById(data.companyId()),
                        data.carrierResponsibleForSafety()
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(TruckRequest data) {
        if (repository.existsByIdIsNotAndUnit(data.id(), data.unit()))
            throw new ExistsException(mSourceBundle.apply("truck.unit.taken"));

        TruckEntity truck = repository.getReferenceById(data.id());

        truck.setUnit(data.unit());
        truck.setInServiceDate(data.inServiceDate());
        truck.setLicensePlate(data.licensePlate());
        truck.setState(stateRepository.getReferenceById(data.stateId()));
        truck.setModelMaker(makerRepository.getReferenceById(data.modelMakerId()));
        truck.setYear(data.year());
        truck.setFuelType(fuelTypeRepository.getReferenceById(data.fuelTypeId()));
        truck.setGrossWeight(data.grossWeight());
        truck.setAxles(data.axles());
        truck.setVin(data.vin());
        truck.setOwnershipType(data.ownershipTypeId() != 0 ? ownershipTypeRepository.getReferenceById(data.ownershipTypeId()) : null);
        truck.setIncludeIFTA(data.includeIFTA());
        truck.setPurchaseType(data.purchaseTypeId() != 0 ? purchaseTypeRepository.getReferenceById(data.purchaseTypeId()) : null);
        truck.setDriver(data.driverId() != 0 ? driverRepository.getReferenceById(data.driverId()) : null);
        truck.setDescription(data.description());
        truck.setCompany(companyRepository.getReferenceById(data.companyId()));
        truck.setCarrierResponsibleForSafety(data.carrierResponsibleForSafety());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse deactivate(Integer id) {
        TruckEntity truck = repository.getReferenceById(id);

        truck.setStatus(TruckStatusEnum.PASSIVE);
        truck.setStatusDate(LocalDateTime.now());

        repository.save(truck);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse activate(Integer id) {
        TruckEntity truck = repository.getReferenceById(id);

        truck.setStatus(TruckStatusEnum.ACTIVE);
        truck.setStatusDate(LocalDateTime.now());

        repository.save(truck);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(TruckRequest data) {
        // not implemented
        return null;
    }

    @Override
    public ApiResponse attachFile(TruckFileRequest data, MultipartFile file) {
        TruckEntity truck = repository.getReferenceById(data.truckId());

        ResourceEntity resource = resourceService.createResource(file, "truck");

        truckFileRepository.save(
                new TruckFileEntity(
                        resource,
                        data.expirationDate(),
                        data.description(),
                        data.type(),
                        TruckFileStatusEnum.ACTIVE,
                        truck
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse attachPermit(int truckId, PermitRequest data, MultipartFile file) {
        TruckEntity truck = repository.getReferenceById(truckId);

        ResourceEntity resource = resourceService.createResource(file, "truck//permits");

        permitRepository.save(
                new PermitEntity(
                        resource,
                        data.expirationDate(),
                        data.description(),
                        data.type(),
                        PermitStatusEnum.ACTIVE,
                        truck
                )
        );

        repository.save(truck);

        return ApiResponse.success();
    }

    @Override
    public DataResponse<List<TruckFileEntity>> getFiles(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TruckFileEntity> cq = cb.createQuery(TruckFileEntity.class);
        Root<TruckFileEntity> truckFiles = cq.from(TruckFileEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("truckId")) {
            Join<TruckFileEntity, TruckEntity> truck = truckFiles.join("truck");
            filters.add(cb.equal(truck.get("id"), params.get("truckId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.truck.missed"));
        }

        cq.select(truckFiles)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(truckFiles.get("id")));

        TypedQuery<TruckFileEntity> query = entityManager.createQuery(cq);
        List<TruckFileEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public DataResponse<List<PermitEntity>> getPermits(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PermitEntity> cq = cb.createQuery(PermitEntity.class);
        Root<PermitEntity> permits = cq.from(PermitEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("truckId")) {
            Join<PermitEntity, TruckEntity> truck = permits.join("truck");
            filters.add(cb.equal(truck.get("id"), params.get("truckId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.truck.missed"));
        }

        cq.select(permits)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(permits.get("id")));

        TypedQuery<PermitEntity> query = entityManager.createQuery(cq);
        List<PermitEntity> results = query.getResultList();

        return DataResponse.success(results);
    }
}
