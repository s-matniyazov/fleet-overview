package fleetoverview.service;

import fleetoverview.data.request.TrailerFileRequest;
import fleetoverview.data.request.TrailerRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.domain.entity.trailer.TrailerEntity;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
public interface TrailerService extends CrudService<TrailerEntity, TrailerRequest> {
    ApiResponse attachFileToTrailer(TrailerFileRequest data, MultipartFile file);
}
