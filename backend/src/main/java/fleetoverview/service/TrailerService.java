package fleetoverview.service;

import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.domain.entity.trailer.TrailerFileEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public interface TrailerService extends CrudService<TrailerEntity, TrailerRequest> {
    ApiResponse attachFile(TrailerFileRequest data, MultipartFile file);
    DataResponse<List<TrailerFileEntity>> getFiles(Map<String, String> params);
}
