package fleetoverview.service.impl;

import fleetoverview.data.request.CollisionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.claim.CollisionEntity;
import fleetoverview.repository.*;
import fleetoverview.service.CollisionService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CollisionServiceImpl extends BaseService implements CollisionService {
    private final CollisionRepository repository;
    private final StateRepository stateRepository;
    private final TruckRepository truckRepository;
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final SqlSessionFactory db;

    public CollisionServiceImpl(CollisionRepository repository, StateRepository stateRepository, TruckRepository truckRepository, DriverRepository driverRepository, CompanyRepository companyRepository, SqlSessionFactory db) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.truckRepository = truckRepository;
        this.driverRepository = driverRepository;
        this.companyRepository = companyRepository;
        this.db = db;
    }

    @Override
    public DataResponse<List<CollisionEntity>> get(Map<String, Object> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        try (SqlSession sqlSession = db.openSession()) {
            return DataResponse.success(
                    sqlSession.selectList("Collision.selectCollisions", params)
            );
        }
    }

    @Override
    public ApiResponse post(CollisionRequest data) {
        CollisionEntity entity = new CollisionEntity();
        mapToEntity(data, entity);
        repository.save(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(CollisionRequest data) {
        CollisionEntity entity = repository.findById(data.id())
                .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("collision.not.found")));
        mapToEntity(data, entity);
        repository.save(entity);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(CollisionRequest data) {
        repository.deleteById(data.id());
        return ApiResponse.success();
    }

    private void mapToEntity(CollisionRequest data, CollisionEntity entity) {
        entity.setReportNumber(data.reportNumber());
        entity.setCollisionDate(data.collisionDate());
        entity.setCollisionTime(data.collisionTime());
        entity.setTimeZone(data.timeZone());
        entity.setCity(data.city());
        entity.setZipCode(data.zipCode());
        entity.setTypeOfCargo(data.typeOfCargo());

        if (data.stateId() != 0) {
            entity.setState(stateRepository.getReferenceById(data.stateId()));
        }
        if (data.truckId() != 0) {
            entity.setTruck(truckRepository.getReferenceById(data.truckId()));
        }
        if (data.driverId() != 0) {
            entity.setDriver(driverRepository.getReferenceById(data.driverId()));
        }
        if (data.companyId() != 0) {
            entity.setCompany(companyRepository.getReferenceById(data.companyId()));
        }
    }
}
