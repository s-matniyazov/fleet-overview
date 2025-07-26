package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.trailer.TrailerTypeEntity;
import fleetoverview.repository.TrailerTypeRepository;
import fleetoverview.service.TrailerTypeService;
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
 * @created : 25 май 2025
 **/
@Service
public class TrailerTypeServiceImpl extends BaseService implements TrailerTypeService {
    private final TrailerTypeRepository repository;

    @Autowired
    public TrailerTypeServiceImpl(TrailerTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<TrailerTypeEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new TrailerTypeEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        TrailerTypeEntity trailerType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailerType.not_found")));

        trailerType.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        TrailerTypeEntity trailerType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("trailerType.not_found")));

        repository.delete(trailerType);

        return ApiResponse.success();
    }
}
