package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.base.BaseIdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
@Entity
@Table(name = "resources")
public class ResourceEntity extends BaseIdEntity {
    private String fileName;
    private String extension;
    private Long size;
    @JsonIgnore
    private String path;
    private String contentType;

    public ResourceEntity() {
    }

    public ResourceEntity(String fileName, String extension, Long size,
                          String path, String contentType) {
        this.fileName = fileName;
        this.extension = extension;
        this.size = size;
        this.path = path;
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
