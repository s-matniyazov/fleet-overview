package fleetoverview.service;

import fleetoverview.domain.entity.ResourceEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public interface ResourceService {
    void downloadResource(Integer id, HttpServletResponse response);
    ResourceEntity createResource(MultipartFile file);
    void removeResource(ResourceEntity resource);
}
