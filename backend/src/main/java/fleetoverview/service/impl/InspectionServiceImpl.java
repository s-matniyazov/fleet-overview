package fleetoverview.service.impl;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.inspection.Inspection;
import fleetoverview.domain.entity.inspection.InspectionFile;
import fleetoverview.domain.entity.inspection.ViolationTypes;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.projection.inspection.InspectionDto;
import fleetoverview.domain.projection.inspection.InspectionFileDto;
import fleetoverview.domain.projection.inspection.ViolationTypesDto;
import fleetoverview.repository.*;
import fleetoverview.service.InspectionService;
import fleetoverview.service.ResourceService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InspectionServiceImpl extends BaseService implements InspectionService {

    private final InspectionRepository inspectionRepo;
    private final InspectionFileRepository inspectionFileRepo;
    private final ResourceService resourceService;
    private final CompanyRepository companyRepo;
    private final DriverRepository driverRepository;
    private final TruckRepository truckRepository;
    private final StateRepository stateRepository;
    private final ViolationTypeRepository violationTypeRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ApiResponse save(InspectionRequest data) {

        Inspection inspection = Optional.ofNullable(data.id())
                .map(id -> inspectionRepo.findById(id)
                        .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("inspection.not_found"))))
                .orElseGet(Inspection::new);

        inspection.setInspectionNumber(data.inspectionNumber());
        inspection.setInspectionDate(data.inspectionDate());
        inspection.setCompany(companyRepo.findById(data.companyId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("company.not_found"))));

        inspection.setDriver(data.driverId() == null ? null :
                driverRepository.findById(data.driverId())
                        .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found"))));

        inspection.setCoDriver(data.coDriverId() == null ? null :
                driverRepository.findById(data.coDriverId())
                        .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("driver.not_found"))));

        inspection.setTruck(data.truckId() == null ? null :
                truckRepository.findById(data.truckId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("truck.not_found"))));

        inspection.setDescription(data.description());
        inspection.setState(stateRepository.findById(data.stateId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("state.not_found"))));
        inspection.setCity(data.city());
        inspection.setLocation(data.location());
        inspection.setViolationDiscovered(data.violationDiscovered());
        inspection.setInspectionLevel(data.inspectionLevel());
        inspection.setTotalFineAmount(data.totalFineAmount());
        inspection.setOutOfService(data.outOfService());
        inspection.setDeadlineAt(data.inspectionDate().plusDays(15));

        inspectionRepo.save(inspection);

        if (data.violationTypes() != null) {
            violationTypeRepository.deleteAllByInspectionId((inspection.getId()));
            List<ViolationTypes> violations = data.violationTypes()
                    .stream()
                    .map(v -> {
                                ViolationTypes vt = new ViolationTypes();
                                vt.setInspection(inspection);
                                vt.setSectionViolCode(v.sectionViolCode());
                                vt.setDescription(v.description());
                                vt.setType(v.type());
                                return vt;
                            }
                    ).collect(Collectors.toList());
            violationTypeRepository.saveAll(violations);
        }
        return ApiResponse.success();
    }

    @Override
    public ApiResponse attachFile(InspectionFileRequest data, MultipartFile file) {
        Inspection inspection = inspectionRepo.findById(data.inspectionId()).orElseThrow(() -> new NotFoundException(mSourceBundle.apply("inpesction.not_found")));
        ResourceEntity resource = resourceService.createResource(file, "inspection");

        InspectionFile inspectionFile = new InspectionFile(resource, data.type(), StatusEnum.ACTIVE, inspection);
        inspectionFileRepo.save(inspectionFile);
        return ApiResponse.success();
    }

    @Override
    public DataResponse<List<InspectionFileDto>> getFiles(Integer inspectionId) {
        var inspectionFiles = inspectionFileRepo.findAllByInspectionIdOrderByIdDesc(inspectionId);
        List<InspectionFileDto> inspectionFileDtos = inspectionFiles.stream().map(i ->
                        new InspectionFileDto(
                                i.getId(),
                                i.getResource(),
                                i.getType().name(),
                                i.getStatus().name(),
                                i.getDescription(),
                                i.getResource().getFileName(),
                                i.getResource().getSize()
                        ))
                .collect(Collectors.toList());
        return DataResponse.success(inspectionFileDtos);
    }

    @Override
    public Page<InspectionDto> getInspections(Pageable page, Integer companyId, String driverName, StatusEnum staus) {
        return inspectionRepo.findAllByCompanyIdAndDriverNameAndStatus(companyId, driverName, staus.name(), page);
    }

    @Override
    public DataResponse<Inspection> get(Integer inspectionId) {
        Inspection inspection = inspectionRepo.findById(inspectionId)
                .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("inspection.not_found")));

        return DataResponse.success(inspection);
    }

    @Override
    public DataResponse<List<ViolationTypesDto>> getViolationTypes(Integer inspectionId) {
        List<ViolationTypes> violationTypes = violationTypeRepository.findByInspectionId(inspectionId);

        List<ViolationTypesDto> violationTypesDtos = violationTypes.stream()
                .map(v ->
                        new ViolationTypesDto(
                                v.getId(),
                                v.getSectionViolCode(),
                                v.getType(),
                                v.getDescription()))
                .toList();

        return DataResponse.success(violationTypesDtos);
    }

    @Override
    public ApiResponse updateStatus(Integer inspectionId) {
        Inspection inspection = inspectionRepo.findById(inspectionId)
                .orElseThrow(() -> new NotFoundException(mSourceBundle.apply("inspection.not_found")));

        inspection.setStatus(inspection.getStatus() == StatusEnum.ACTIVE ? StatusEnum.PASSIVE : StatusEnum.ACTIVE);
        inspectionRepo.save(inspection);
        return ApiResponse.success();
    }

}
