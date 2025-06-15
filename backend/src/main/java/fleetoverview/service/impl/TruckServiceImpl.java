package fleetoverview.service.impl;

import fleetoverview.data.request.PermitRequest;
import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.*;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.entity.truck.TruckFileEntity;
import fleetoverview.domain.enums.PermitStatusEnum;
import fleetoverview.domain.enums.truck.TruckFileStatusEnum;
import fleetoverview.repository.*;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TruckService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public TruckServiceImpl(ResourceService resourceService, TruckRepository repository, StateRepository stateRepository, TruckModelMakerRepository makerRepository,
                            FuelTypeRepository fuelTypeRepository, OwnershipTypeRepository ownershipTypeRepository,
                            PurchaseTypeRepository purchaseTypeRepository, DriverRepository driverRepository, CompanyRepository companyRepository) {
        this.resourceService = resourceService;
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.makerRepository = makerRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public DataResponse<List<TruckEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TruckEntity> cq = cb.createQuery(TruckEntity.class);
        Root<TruckEntity> trucks = cq.from(TruckEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("companyId")) {
            Join<TruckEntity, CompanyEntity> company = trucks.join("company");
            filters.add(cb.equal(company.get("id"), params.get("companyId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        cq.select(trucks)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(trucks.get("id")));

        TypedQuery<TruckEntity> query = entityManager.createQuery(cq);
        List<TruckEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public ApiResponse post(TruckRequest data) {
        repository.save(
                new TruckEntity(
                        data.unit(),
                        data.inServiceDate(),
                        data.licensePlate(),
                        stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))),
                        makerRepository.findById(data.modelMakerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))),
                        data.year(),
                        fuelTypeRepository.findById(data.fuelTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found"))),
                        data.grossWeight(),
                        data.axles(),
                        data.vin(),
                        ownershipTypeRepository.findById(data.ownershipTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))),
                        data.includeIFTA(),
                        purchaseTypeRepository.findById(data.purchaseTypeId()).orElse(null),
                        driverRepository.findById(data.driverId()).orElse(null),
                        data.description(),
                        companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found")))
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(TruckRequest data) {
        TruckEntity truck = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        truck.setUnit(data.unit());
        truck.setInServiceDate(data.inServiceDate());
        truck.setLicensePlate(data.licensePlate());
        truck.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));
        truck.setModelMaker(makerRepository.findById(data.modelMakerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))));
        truck.setYear(data.year());
        truck.setFuelType(fuelTypeRepository.findById(data.fuelTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found"))));
        truck.setGrossWeight(data.grossWeight());
        truck.setAxles(data.axles());
        truck.setVin(data.vin());
        truck.setOwnershipType(ownershipTypeRepository.findById(data.ownershipTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))));
        truck.setIncludeIFTA(data.includeIFTA());
        truck.setPurchaseType(purchaseTypeRepository.findById(data.purchaseTypeId()).orElse(null));
        truck.setDriver(driverRepository.findById(data.driverId()).orElse(null));
        truck.setDescription(data.description());
        truck.setCompany(companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(TruckRequest data) {
        // not implemented
        return null;
    }

    @Override
    public ApiResponse attachFile(TruckFileRequest data, MultipartFile file) {
        TruckEntity truck = repository.findById(data.truckId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        ResourceEntity resource = resourceService.createResource(file, "truck");

        truck.getFiles().add(
                new TruckFileEntity(
                        resource,
                        data.expirationDate(),
                        data.description(),
                        data.type(),
                        TruckFileStatusEnum.ACTIVE,
                        truck
                )
        );

        repository.save(truck);

        return ApiResponse.success();
    }

    @Override
    public ApiResponse attachPermit(int truckId, PermitRequest data, MultipartFile file) {
        TruckEntity truck = repository.findById(truckId).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        ResourceEntity resource = resourceService.createResource(file, "truck//permits");

        truck.getPermits().add(
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
