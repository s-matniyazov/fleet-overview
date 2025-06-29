package fleetoverview.domain.projection;

import fleetoverview.domain.enums.PermitStatusEnum;
import fleetoverview.domain.enums.PermitTypeEnum;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public class PermitProjection {
    private int id;
    private int resourcesId;
    private String fileName;
    private String extension;
    private long size;
    private String contentType;

    private LocalDate expirationDate;

    private String description;

    private PermitTypeEnum type;

    private PermitStatusEnum status;

    public PermitProjection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(int resourcesId) {
        this.resourcesId = resourcesId;
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

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermitTypeEnum getType() {
        return type;
    }

    public void setType(PermitTypeEnum type) {
        this.type = type;
    }

    public PermitStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PermitStatusEnum status) {
        this.status = status;
    }
}
