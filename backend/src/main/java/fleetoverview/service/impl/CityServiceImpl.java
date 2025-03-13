package fleetoverview.service.impl;

import fleetoverview.data.request.CityRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.CityEntity;
import fleetoverview.repository.CityRepository;
import fleetoverview.repository.CountryRepository;
import fleetoverview.service.CityService;
import fleetoverview.service.base.BaseService;
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
public class CityServiceImpl extends BaseService implements CityService {
    private final CityRepository repository;
    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository repository, CountryRepository countryRepository) {
        this.repository = repository;
        this.countryRepository = countryRepository;
    }

    @Override
    public DataResponse<List<CityEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll());
    }

    @Override
    public ApiResponse post(CityRequest data) {
        repository.save(
                new CityEntity(
                        data.name(),
                        countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found")))
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(CityRequest data) {
        CityEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        city.setName(data.name());
        city.setCountry(countryRepository.findById(data.countryId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("country.not_found"))));

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(CityRequest data) {
        CityEntity city = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found")));

        repository.delete(city);

        return ApiResponse.success();
    }
}
