package fleetoverview.service;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionLevelRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.inspection.InspectionFile;
import fleetoverview.domain.entity.inspection.InspectionLevelsRef;
import fleetoverview.domain.projection.inspection.InspectionDto;
import fleetoverview.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface InspectionLevelService{
    DataResponse<List<InspectionLevelsRef>> getAll();
    ApiResponse save(InspectionLevelRequest data);
    ApiResponse delete(Integer id);
}
