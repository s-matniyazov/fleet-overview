package fleetoverview.service;

import fleetoverview.data.request.InspectionLevelRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.inspection.InspectionLevelsRef;

import java.util.List;

public interface InspectionLevelService{
    DataResponse<List<InspectionLevelsRef>> getAll();
    ApiResponse save(InspectionLevelRequest data);
    ApiResponse delete(Integer id);
}
