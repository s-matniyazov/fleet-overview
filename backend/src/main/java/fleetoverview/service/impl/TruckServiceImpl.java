package fleetoverview.service.impl;

import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.TruckEntity;
import fleetoverview.domain.entity.TruckFileEntity;
import fleetoverview.domain.entity.enums.TruckFileTypeEnum;
import fleetoverview.repository.*;
import fleetoverview.service.ResourceService;
import fleetoverview.service.TruckService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
@Service
public class TruckServiceImpl extends BaseService implements TruckService {
    private final ResourceService resourceService;
    private final TruckRepository repository;
    private final CityRepository cityRepository;
    private final ModelMakerRepository makerRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final OwnershipTypeRepository ownershipTypeRepository;
    private final PurchaseTypeRepository purchaseTypeRepository;
    private final OwnerOperatorRepository ownerOperatorRepository;

    @Autowired
    public TruckServiceImpl(ResourceService resourceService, TruckRepository repository, CityRepository cityRepository, ModelMakerRepository makerRepository,
                            FuelTypeRepository fuelTypeRepository, OwnershipTypeRepository ownershipTypeRepository,
                            PurchaseTypeRepository purchaseTypeRepository, OwnerOperatorRepository ownerOperatorRepository) {
        this.resourceService = resourceService;
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
        TruckEntity truck = repository.findById(data.id()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        truck.setUnit(data.unit());
        truck.setInServiceDate(data.inServiceDate());
        truck.setLicensePlate(data.licensePlate());
        truck.setCity(cityRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("city.not_found"))));
        truck.setModelMaker(makerRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("maker.not_found"))));
        truck.setYear(data.year());
        truck.setFuelType(fuelTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("fuelType.not_found"))));
        truck.setGrossWeight(data.grossWeight());
        truck.setAxles(data.axles());
        truck.setVin(data.vin());
        truck.setOwnershipType(ownershipTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownershipType.not_found"))));
        truck.setIncludeIFTA(data.includeIFTA());
        truck.setPurchaseType(purchaseTypeRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("purchaseType.not_found"))));
        truck.setOwnerOperator(ownerOperatorRepository.findById(data.cityId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("ownerOperator.not_found"))));
        truck.setDescription(data.description());

        return null;
    }

    @Override
    public ApiResponse delete(TruckRequest data) {
        return null;
    }

    @Override
    public ApiResponse attachFileToTruck(TruckFileRequest data, MultipartFile file) {
        TruckEntity truck = repository.findById(data.truckId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found")));

        ResourceEntity resource = resourceService.createResource(file);

        truck.getFiles().add(
                new TruckFileEntity(
                        resource,
                        null,
                        "description",
                        TruckFileTypeEnum.ANN_INS,
                        truck
                )
        );

        repository.save(truck);

        return ApiResponse.success();
    }
}
