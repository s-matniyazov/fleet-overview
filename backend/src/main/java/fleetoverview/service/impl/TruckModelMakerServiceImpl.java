package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.truck.TruckModelMakerEntity;
import fleetoverview.repository.TruckModelMakerRepository;
import fleetoverview.service.TruckModelMakerService;
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
public class TruckModelMakerServiceImpl extends BaseService implements TruckModelMakerService {
    private final TruckModelMakerRepository repository;

    @Autowired
    public TruckModelMakerServiceImpl(TruckModelMakerRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<TruckModelMakerEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new TruckModelMakerEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        TruckModelMakerEntity modelMaker = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("modelMaker.not_found")));

        modelMaker.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        TruckModelMakerEntity modelMaker = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("modelMaker.not_found")));

        repository.delete(modelMaker);

        return ApiResponse.success();
    }
}
