package fleetoverview.service;

import fleetoverview.data.request.TruckFileRequest;
import fleetoverview.data.request.TruckRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.TruckEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 16 март 2025
 **/
public interface TruckService extends CrudService<TruckEntity, TruckRequest> {
    ApiResponse attachFileToTruck(TruckFileRequest data, MultipartFile file);
}
