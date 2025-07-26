package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.truck.FuelTypeEntity;
import fleetoverview.repository.FuelTypeRepository;
import fleetoverview.service.FuelTypeService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Service
public class FuelTypeServiceImpl extends BaseService implements FuelTypeService {
    private final FuelTypeRepository repository;

    @Autowired
    public FuelTypeServiceImpl(FuelTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<FuelTypeEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new FuelTypeEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        FuelTypeEntity fuelType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found")));

        fuelType.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        FuelTypeEntity fuelType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found")));

        repository.delete(fuelType);

        return ApiResponse.success();
    }
}
