package fleetoverview.service.impl;

import fleetoverview.data.request.OnlyNameRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.PurchaseTypeEntity;
import fleetoverview.repository.PurchaseTypeRepository;
import fleetoverview.service.PurchaseTypeService;
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
public class PurchaseTypeServiceImpl extends BaseService implements PurchaseTypeService {
    private final PurchaseTypeRepository repository;

    @Autowired
    public PurchaseTypeServiceImpl(PurchaseTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<PurchaseTypeEntity>> get(Map<String, Object> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(OnlyNameRequest data) {
        repository.save(
                new PurchaseTypeEntity(data.name())
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(OnlyNameRequest data) {
        PurchaseTypeEntity purchaseType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("purchaseType.not_found")));

        purchaseType.setName(data.name());

        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(OnlyNameRequest data) {
        PurchaseTypeEntity purchaseType = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("purchaseType.not_found")));

        repository.delete(purchaseType);

        return ApiResponse.success();
    }
}
