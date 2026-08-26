package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CountryEntity;
import fleetoverview.repository.CountryRepository;
import fleetoverview.service.CountryService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
public class CountryServiceImpl extends BaseService implements CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryServiceImpl(CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<CountryEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    @Transactional
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new CountryEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    @Transactional
    public ApiResponse put(OnlyNameRequest data) {
        CountryEntity country = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")));

        country.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        CountryEntity country = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")));

        repository.delete(country);

        return ApiResponse.success();
    }
}
