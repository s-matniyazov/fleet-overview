package fleetoverview.service.impl;

import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.TruckEntity;
import fleetoverview.repository.*;
import fleetoverview.service.TruckService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
@Service
public class TruckServiceImpl extends BaseService implements TruckService {
    private final TruckRepository repository;
    private final CityRepository cityRepository;
    private final ModelMakerRepository makerRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final OwnerOperatorRepository ownerOperatorRepository;

    @Autowired
    public TruckServiceImpl(TruckRepository repository, CityRepository cityRepository, ModelMakerRepository makerRepository,
                            FuelTypeRepository fuelTypeRepository, OwnershipTypeRepository ownershipTypeRepository,
                            PurchaseTypeRepository purchaseTypeRepository, OwnerOperatorRepository ownerOperatorRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
        this.makerRepository = makerRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.ownershipTypeRepository = ownershipTypeRepository;
        this.purchaseTypeRepository = purchaseTypeRepository;
        this.ownerOperatorRepository = ownerOperatorRepository;
    }

    @Override
    public DataResponse<List<TruckEntity>> get(Map<String, String> params) {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.DESC, "id")));
    }

    @Override
    public ApiResponse post(TruckRequest data) {
        repository.save(
                new TruckEntity(
                        data.unit(),
                        data.inServiceDate(),
                        data.licensePlate(),
                        cityRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found"))),
                        makerRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))),
                        data.year(),
                        fuelTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found"))),
                        data.grossWeight(),
                        data.axles(),
                        data.vin(),
                        ownershipTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))),
                        data.includeIFTA(),
                        purchaseTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("purchaseType.not_found"))),
                        ownerOperatorRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found"))),
                        data.description()
                )
        );
        return ApiResponse.success();
    }

    @Override
    public ApiResponse put(TruckRequest data) {
        return null;
    }

    @Override
    public ApiResponse delete(TruckRequest data) {
        return null;
    }
}
