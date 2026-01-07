package fleetoverview.service;

import fleetoverview.data.request.CompanyFileRequest;
import fleetoverview.data.request.CompanyRequest;
import fleetoverview.data.response.ApiResponse;
import fleetoverview.data.response.DataResponse;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.company.CompanyFileEntity;
import fleetoverview.domain.projection.company.CompanyProjection;
import fleetoverview.service.base.CrudService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 03 май 2025
 **/
public interface CompanyService extends CrudService<CompanyEntity, CompanyRequest> {
    ApiResponse attachFile(CompanyFileRequest data, MultipartFile file);
    DataResponse<List<CompanyFileEntity>> getFiles(Map<String, String> params);
    DataResponse<List<CompanyProjection>> getWithFiles(Map<String, String> params);
    ApiResponse deactivate(Integer id);
    ApiResponse activate(Integer id);
}
