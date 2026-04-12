package fleetoverview.service.impl;

import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.entity.trailer.TrailerFileEntity;
import fleetoverview.domain.enums.trailer.TrailerFileStatusEnum;
import fleetoverview.domain.enums.trailer.TrailerStatusEnum;
import fleetoverview.repository.*;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TrailerService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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
        if (repository.existsByUnit(data.unit()))
            throw new ExistsException(mSourceBundle.apply("trailer.unit.taken"));

        repository.save(
                new TrailerEntity(
                        data.unit(),
                        data.licensePlate(),
                        data.inServiceDate(),
                        makerRepository.getReferenceById(data.modelMakerId()),
                        trailerTypeRepository.getReferenceById(data.typeId()),
                        data.year(),
                        data.axles(),
                        data.length(),
                        data.height(),
                        data.vin(),
                        ownershipTypeRepository.getReferenceById(data.ownershipTypeId()),
                        data.purchaseTypeId() != 0 ? purchaseTypeRepository.getReferenceById(data.purchaseTypeId()) : null,
                        data.driverId() != 0 ? driverRepository.getReferenceById(data.driverId()) : null,
                        data.description(),
                        companyRepository.getReferenceById(data.companyId())
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(TrailerRequest data) {
        if (repository.existsByIdIsNotAndUnit(data.id(), data.unit()))
            throw new ExistsException(mSourceBundle.apply("truck.unit.taken"));

        TrailerEntity trailer = repository.getReferenceById(data.id());

        trailer.setUnit(data.unit());
        trailer.setLicensePlate(data.licensePlate());
        trailer.setInServiceDate(data.inServiceDate());
        trailer.setModelMaker(makerRepository.getReferenceById(data.modelMakerId()));
        trailer.setType(trailerTypeRepository.getReferenceById(data.typeId()));
        trailer.setYear(data.year());
        trailer.setAxles(data.axles());
        trailer.setLength(data.length());
        trailer.setHeight(data.height());
        trailer.setVin(data.vin());
        trailer.setOwnershipType(data.ownershipTypeId() != 0 ? ownershipTypeRepository.getReferenceById(data.ownershipTypeId()) : null);
        trailer.setPurchaseType(data.purchaseTypeId() != 0 ? purchaseTypeRepository.getReferenceById(data.purchaseTypeId()) : null);
        trailer.setDriver(data.driverId() != 0 ? driverRepository.getReferenceById(data.driverId()) : null);
        trailer.setDescription(data.description());
        trailer.setCompany(companyRepository.getReferenceById(data.companyId()));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(TrailerRequest data) {
        // not implemented
        return null;
    }

    @Override
    public ApiResponse deactivate(Integer id) {
        TrailerEntity trailer = repository.getReferenceById(id);
        trailer.setStatus(TrailerStatusEnum.PASSIVE);
        repository.save(trailer);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse activate(Integer id) {
        TrailerEntity trailer = repository.getReferenceById(id);
        trailer.setStatus(TrailerStatusEnum.ACTIVE);
        repository.save(trailer);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse attachFile(TrailerFileRequest data, MultipartFile file) {
        TrailerEntity trailer = repository.getReferenceById(data.trailerId());

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
