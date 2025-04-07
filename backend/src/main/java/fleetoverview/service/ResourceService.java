package fleetoverview.service;

import fleetoverview.domain.entity.ResourceEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
public interface ResourceService {
    ResourceEntity createResource(MultipartFile file);
    void removeResource(ResourceEntity resource);
}
