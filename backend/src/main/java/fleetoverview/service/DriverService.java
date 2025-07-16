package fleetoverview.service;

import fleetoverview.data.request.DriverFileRequest;
import fleetoverview.data.request.DriverRequest;
import fleetoverview.data.request.TerminationRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.entity.driver.DriverFileEntity;
import fleetoverview.domain.entity.driver.EndorsementEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface DriverService extends CrudService<DriverEntity, DriverRequest> {
    ApiResponse terminate(TerminationRequest data);
    ApiResponse attachFile(DriverFileRequest data, MultipartFile file);
    DataResponse<List<DriverFileEntity>> getFiles(Map<String, String> params);
    DataResponse<List<EndorsementEntity>> getEndorsements();
}
