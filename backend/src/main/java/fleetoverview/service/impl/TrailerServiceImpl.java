package fleetoverview.service.impl;

import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.entity.trailer.TrailerFileEntity;
import fleetoverview.domain.enums.trailer.TrailerFileStatusEnum;
import fleetoverview.repository.*;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TrailerService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
    private final TrailerFileRepository trailerFileRepository;

    @Autowired
    private EntityManager entityManager;

    private final SqlSessionFactory db;

    public TrailerServiceImpl(ResourceService resourceService, TrailerRepository repository, TrailerTypeRepository trailerTypeRepository,
                              TrailerModelMakerRepository makerRepository, OwnershipTypeRepository ownershipTypeRepository,
                              PurchaseTypeRepository purchaseTypeRepository, DriverRepository driverRepository,
                              CompanyRepository companyRepository, TrailerFileRepository trailerFileRepository, SqlSessionFactory db) {
        this.resourceService = resourceService;
        this.repository = repository;
        this.trailerTypeRepository = trailerTypeRepository;
        this.makerRepository = makerRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
        this.trailerFileRepository = trailerFileRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<TrailerEntity>> get(Map<String, Object> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("selectTrailers", params)
            );
        }
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
    public ApiResponse attachFile(TrailerFileRequest data, MultipartFile file) {
        TrailerEntity trailer = repository.findById(data.trailerId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailer.not_found")));

        ResourceEntity resource = resourceService.createResource(file, "trailer");

        trailerFileRepository.save(
                new TrailerFileEntity(
                        resource,
                        data.expirationDate(),
                        data.description(),
                        data.type(),
                        TrailerFileStatusEnum.ACTIVE,
                        trailer
                )
        );

        return ApiResponse.success();
    }

    @Override
    public DataResponse<List<TrailerFileEntity>> getFiles(Map<String, String> params) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TrailerFileEntity> cq = cb.createQuery(TrailerFileEntity.class);
        Root<TrailerFileEntity> trailerFiles = cq.from(TrailerFileEntity.class);

        List<Predicate> filters = new ArrayList<>();

        if (params.containsKey("trailerId")) {
            Join<TrailerFileEntity, TrailerEntity> trailer = trailerFiles.join("trailer");
            filters.add(cb.equal(trailer.get("id"), params.get("trailerId")));
        } else {
            throw new NotFoundException(mSourceBundle.apply("filter.trailer.missed"));
        }

        cq.select(trailerFiles)
                .where(cb.and(filters.stream().filter(Objects::nonNull).toArray(Predicate[]::new)))
                .orderBy(cb.desc(trailerFiles.get("id")));

        TypedQuery<TrailerFileEntity> query = entityManager.createQuery(cq);
        List<TrailerFileEntity> results = query.getResultList();

        return DataResponse.success(results);
    }
}
