package fleetoverview.service.impl;

import fleetoverview.data.request.InspectionLevelRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.inspection.InspectionLevelsRef;
import fleetoverview.domain.projection.inspection.InspectionDto;
import fleetoverview.domain.projection.inspection.InspectionLevelDto;
import fleetoverview.repository.InspectionLevelRepository;
import fleetoverview.service.InspectionLevelService;
import fleetoverview.service.base.BaseService;
import fleetoverview.util.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InspectionLevelServiceImpl extends BaseService implements InspectionLevelService {
    private final InspectionLevelRepository repository;

    public InspectionLevelServiceImpl(InspectionLevelRepository repository) {
        this.repository = repository;
    }

    @Override
    public DataResponse<List<InspectionLevelsRef>> getAll() {
        return DataResponse.success(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));

    }

    @Override
    public ApiResponse save(InspectionLevelRequest data) {
        InspectionLevelsRef inspectionLevel = repository.findById(data.id()).orElse(new InspectionLevelsRef());
        inspectionLevel.setLevel(data.level());
        inspectionLevel.setDescription(data.description());
        repository.save(inspectionLevel);
        return ApiResponse.success();
    }

    @Override
    public ApiResponse delete(Integer id) {
        repository.deleteById(id);
        return ApiResponse.success();
    }
}
