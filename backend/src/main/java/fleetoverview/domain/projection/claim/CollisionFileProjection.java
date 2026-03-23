package fleetoverview.domain.projection.claim;

import fleetoverview.domain.enums.claim.CollisionFileStatusEnum;
import fleetoverview.domain.enums.claim.CollisionFileTypeEnum;

import java.time.LocalDate;

public class CollisionFileProjection {
    private int id;
    private int resourcesId;
    private String fileName;
    private String extension;
    private long size;
    private String contentType;
    private String description;
    private CollisionFileTypeEnum type;
    private CollisionFileStatusEnum status;

    public CollisionFileProjection() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollisionFileTypeEnum getType() {
        return type;
    }

    public void setType(CollisionFileTypeEnum type) {
        this.type = type;
    }

    public CollisionFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CollisionFileStatusEnum status) {
        this.status = status;
    }
}
