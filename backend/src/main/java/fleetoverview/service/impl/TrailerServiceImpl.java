package fleetoverview.service.impl;

import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.entity.trailer.TrailerFileEntity;
import fleetoverview.domain.enums.trailer.TrailerFileStatusEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.DriverRepository;
import fleetoverview.repository.OwnershipTypeRepository;
import fleetoverview.repository.PurchaseTypeRepository;
import fleetoverview.repository.TrailerModelMakerRepository;
import fleetoverview.repository.TrailerRepository;
import fleetoverview.repository.TrailerTypeRepository;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TrailerService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
@Service
public class TrailerServiceImpl extends BaseService implements TrailerService {
    private final ResourceService resourceService;
    private final TrailerRepository repository;
    private final TrailerTypeRepository trailerTypeRepository;
    private final TrailerModelMakerRepository makerRepository;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    public TrailerServiceImpl(ResourceService resourceService, TrailerRepository repository, TrailerTypeRepository trailerTypeRepository,
                              TrailerModelMakerRepository makerRepository, OwnershipTypeRepository ownershipTypeRepository,
                              PurchaseTypeRepository purchaseTypeRepository, DriverRepository driverRepository,
                              CompanyRepository companyRepository) {
        this.resourceService = resourceService;
        this.repository = repository;
        this.trailerTypeRepository = trailerTypeRepository;
        this.makerRepository = makerRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public DataResponse<List<TrailerEntity>> get(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrailerEntity> cq = cb.createQuery(TrailerEntity.class);
        Root<TrailerEntity> trailers = cq.from(TrailerEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("companyId")) {
            Join<TrailerEntity, CompanyEntity> company = trailers.join("company");
            filters.add(cb.equal(company.get("id"), params.get("companyId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        cq.select(trailers)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(trailers.get("id")));

        TypedQuery<TrailerEntity> query = entityManager.createQuery(cq);
        List<TrailerEntity> results = query.getResultList();

        return DataResponse.success(results);
    }

    @Override
    public ApiResponse post(TrailerRequest data) {
        repository.save(
                new TrailerEntity(
                        data.unit(),
                        data.licensePlate(),
                        data.inServiceDate(),
                        makerRepository.findById(data.modelMakerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))),
                        trailerTypeRepository.findById(data.typeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailer_type.not_found"))),
                        data.year(),
                        data.axles(),
                        data.length(),
                        data.height(),
                        data.vin(),
                        ownershipTypeRepository.findById(data.ownershipTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))),
                        purchaseTypeRepository.findById(data.purchaseTypeId()).orElse(null),
                        driverRepository.findById(data.driverId()).orElse(null),
                        data.description(),
                        companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found")))
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(TrailerRequest data) {
        TrailerEntity trailer = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailer.not_found")));

        trailer.setUnit(data.unit());
        trailer.setLicensePlate(data.licensePlate());
        trailer.setInServiceDate(data.inServiceDate());
        trailer.setModelMaker(makerRepository.findById(data.modelMakerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))));
        trailer.setType(trailerTypeRepository.findById(data.typeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailer.type.not_found"))));
        trailer.setYear(data.year());
        trailer.setAxles(data.axles());
        trailer.setLength(data.length());
        trailer.setHeight(data.height());
        trailer.setVin(data.vin());
        trailer.setOwnershipType(ownershipTypeRepository.findById(data.ownershipTypeId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))));
        trailer.setPurchaseType(purchaseTypeRepository.findById(data.purchaseTypeId()).orElse(null));
        trailer.setDriver(driverRepository.findById(data.driverId()).orElse(null));
        trailer.setDescription(data.description());
        trailer.setCompany(companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(TrailerRequest data) {
        // not implemented
        return null;
    }

    @Override
    public ApiResponse attachFileToTrailer(TrailerFileRequest data, MultipartFile file) {
        TrailerEntity trailer = repository.findById(data.trailerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailer.not_found")));

        ResourceEntity resource = resourceService.createResource(file);

        trailer.getFiles().add(
                new TrailerFileEntity(
                        resource,
                        data.expirationDate(),
                        data.description(),
                        data.type(),
                        TrailerFileStatusEnum.ACTIVE,
                        trailer
                )
        );

        repository.save(trailer);

        return ApiResponse.success();
    }
}
