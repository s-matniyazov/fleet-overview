package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.OwnershipTypeEntity;
import fleetoverview.repository.OwnershipTypeRepository;
import fleetoverview.service.OwnershipTypeService;
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
public class OwnershipTypeServiceImpl extends BaseService implements OwnershipTypeService {
    private final OwnershipTypeRepository repository;

    @Autowired
    public OwnershipTypeServiceImpl(OwnershipTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<OwnershipTypeEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new OwnershipTypeEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        OwnershipTypeEntity ownershipType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found")));

        ownershipType.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        OwnershipTypeEntity ownershipType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found")));

        repository.delete(ownershipType);

        return ApiResponse.success();
    }
}
