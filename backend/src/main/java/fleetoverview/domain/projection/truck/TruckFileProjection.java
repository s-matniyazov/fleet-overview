package fleetoverview.domain.projection.truck;

import fleetoverview.domain.enums.truck.TruckFileStatusEnum;
import fleetoverview.domain.enums.truck.TruckFileTypeEnum;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public class TruckFileProjection {
    private int id;
    private int resourcesId;
    private String fileName;
    private String extension;
    private long size;
    private String contentType;

    private LocalDate expirationDate;

    private String description;

    private TruckFileTypeEnum type;

    private TruckFileStatusEnum status;

    public TruckFileProjection() {
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

    public TruckFileTypeEnum getType() {
        return type;
    }

    public void setType(TruckFileTypeEnum type) {
        this.type = type;
    }

    public TruckFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TruckFileStatusEnum status) {
        this.status = status;
    }
}
