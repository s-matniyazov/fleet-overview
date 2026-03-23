package fleetoverview.service;

import fleetoverview.data.request.InspectionFileRequest;
import fleetoverview.data.request.InspectionRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.projection.inspection.InspectionDto;
import fleetoverview.domain.projection.inspection.InspectionFileDto;
import fleetoverview.domain.projection.inspection.ViolationTypesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface InspectionService {
    ApiResponse get(Integer inspectionId);
    ApiResponse save(InspectionRequest data);
    ApiResponse attachFile(InspectionFileRequest data, MultipartFile file);
    DataResponse<List<InspectionFileDto>> getFiles(Integer inspectionId);
    Page<InspectionDto> getInspections(Pageable page, Integer companyId, String driverName, StatusEnum status);
    DataResponse<List<ViolationTypesDto>> getViolationTypes(Integer inspectionId);
    ApiResponse updateStatus(Integer inspectionId);
}
