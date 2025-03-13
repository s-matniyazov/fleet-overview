package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.OwnerOperatorEntity;
import fleetoverview.repository.OwnerOperatorRepository;
import fleetoverview.service.OwnerOperatorService;
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
public class OwnerOperatorServiceImpl extends BaseService implements OwnerOperatorService {
    private final OwnerOperatorRepository repository;

    @Autowired
    public OwnerOperatorServiceImpl(OwnerOperatorRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<OwnerOperatorEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll());
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new OwnerOperatorEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        OwnerOperatorEntity ownerOperator = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found")));

        ownerOperator.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        OwnerOperatorEntity ownerOperator = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found")));

        repository.delete(ownerOperator);

        return ApiResponse.success();
    }
}
