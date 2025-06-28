package fleetoverview.service.impl;

import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CompanyEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.repository.CompanyRepository;
import fleetoverview.repository.DriverRepository;
import fleetoverview.repository.StateRepository;
import fleetoverview.repository.TruckRepository;
import fleetoverview.service.DriverService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class DriverServiceImpl extends BaseService implements DriverService {
    private final DriverRepository repository;
    private final StateRepository stateRepository;
    private final CompanyRepository companyRepository;
    private final TruckRepository truckRepository;


    @Autowired
    private EntityManager entityManager;


    @Autowired
    public DriverServiceImpl(DriverRepository repository, StateRepository stateRepository, CompanyRepository companyRepository, TruckRepository truckRepository) {
        this.repository = repository;
        this.stateRepository = stateRepository;
        this.companyRepository = companyRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    public DataResponse<List<DriverEntity>> get(Map<String, String> params) {
        if (!params.containsKey("companyId")) {
            throw new NotFoundException(mSourceBundle.apply("filter.company.missed"));
        }

        return DataResponse.success(
                repository.findAllByCompanyId(Integer.parseInt(params.get("companyId")))
        );
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
                        data.dateOfBirth(),
                        stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))),
                        data.address(),
                        data.city(),
                        data.zipCode(),
                        data.email(),
                        data.phone(),
                        data.status(),
                        data.type(),
                        truckRepository.findById(data.truckId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")))
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(DriverRequest data) {
        DriverEntity driver = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        driver.setFirstName(data.firstName());
        driver.setMiddleName(data.middleName());
        driver.setLastName(data.lastName());
        driver.setAddress(data.address());
        driver.setCity(data.city());
        driver.setEmail(data.email());
        driver.setPhone(data.phone());
        driver.setZipCode(data.zipCode());
        driver.setCompany(companyRepository.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));
        driver.setHireDate(data.hireDate());
        driver.setDateOfBirth(data.dateOfBirth());
        driver.setStatus(data.status());
        driver.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(DriverRequest data) {
        DriverEntity driver = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found")));

        repository.delete(driver);

        return ApiResponse.success();
    }


}
