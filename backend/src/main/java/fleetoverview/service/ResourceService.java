package fleetoverview.service;

import fleetoverview.domain.entity.ResourceEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public interface ResourceService {
    void downloadResource(Integer id, HttpServletResponse response);
    ResourceEntity createResource(MultipartFile file, String type);
    void removeResource(ResourceEntity resource);
}
