package fleetoverview.service;

import fleetoverview.data.request.PermitRequest;
import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.PermitEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.entity.truck.TruckFileEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


public interface TruckService extends CrudService<TruckEntity, TruckRequest> {
    ApiResponse deactivate(Integer id);
    ApiResponse activate(Integer id);
    ApiResponse attachFile(TruckFileRequest data, MultipartFile file);
    ApiResponse attachPermit(int truckId, PermitRequest data, MultipartFile file);
    DataResponse<List<TruckFileEntity>> getFiles(Map<String, String> params);
    DataResponse<List<PermitEntity>> getPermits(Map<String, String> params);
}
