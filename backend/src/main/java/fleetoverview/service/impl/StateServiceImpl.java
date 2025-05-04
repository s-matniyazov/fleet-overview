package fleetoverview.service.impl;


import fleetoverview.data.request.StateRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.repository.CountryRepository;
import fleetoverview.repository.StateRepository;

import fleetoverview.service.StateService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.ExistsException;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Service
public class StateServiceImpl extends BaseService implements StateService {
    private final StateRepository repository;
    private final CountryRepository countryRepository;

    @Autowired
    public StateServiceImpl(StateRepository repository, CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResponse<List<StateEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll());
    }

    @Override
    public ApiResponse post(StateRequest data) {
        repository.findByNameAndState_Id(data.name(), data.countryId()).ifPresent((city) -> {
            throw new ExistsException(mSourceBundle.apply("city.not_found"));
        });

        repository.save(
                new StateEntity(
                        data.name(),
                        countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")))
                )
        );

        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(StateRequest data) {
        StateEntity state = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found")));

        state.setName(data.name());
        state.setCountry(countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(StateRequest data) {
        StateEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        repository.delete(city);

        return ApiResponse.success();
    }
}
