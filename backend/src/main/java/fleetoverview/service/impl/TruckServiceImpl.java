package fleetoverview.service.impl;

import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.*;
import fleetoverview.domain.enums.TruckFileStatusEnum;
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
    private final ModelMakerRepository makerRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final OwnerOperatorRepository ownerOperatorRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public TruckServiceImpl(ResourceService resourceService, TruckRepository repository, StateRepository stateRepository, ModelMakerRepository makerRepository,
                            FuelTypeRepository fuelTypeRepository, OwnershipTypeRepository ownershipTypeRepository,
                            PurchaseTypeRepository purchaseTypeRepository, OwnerOperatorRepository ownerOperatorRepository, CompanyRepository companyRepository) {
        this.resourceService = resourceService;
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.makerRepository = makerRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.ownerOperatorRepository = ownerOperatorRepository;
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
//        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
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
                        ownerOperatorRepository.findById(data.ownerOperatorId()).orElse(null),
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
        truck.setOwnerOperator(ownerOperatorRepository.findById(data.ownerOperatorId()).orElse(null));
        truck.setDescription(data.description());
        truck.setCompany(companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));

        return null;
    }

    @Override
    public ApiResponse delete(TruckRequest data) {
        return null;
    }

    @Override
    public ApiResponse attachFileToTruck(TruckFileRequest data, MultipartFile file) {
        TruckEntity truck = repository.findById(data.truckId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        ResourceEntity resource = resourceService.createResource(file);

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
}
