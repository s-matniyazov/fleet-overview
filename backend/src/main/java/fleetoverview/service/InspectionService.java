package fleetoverview.service;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.inspection.InspectionEntity;
import fleetoverview.domain.entity.inspection.InspectionFileEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface InspectionService extends CrudService<InspectionEntity, InspectionRequest> {
    ApiResponse attachFile(InspectionFileRequest data, MultipartFile file);
    DataResponse<List<InspectionFileEntity>> getFiles(Map<String, String> params);
}
