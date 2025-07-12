package fleetoverview.domain.projection.company;

import fleetoverview.domain.enums.company.CompanyFileStatusEnum;
import fleetoverview.domain.enums.company.CompanyFileTypeEnum;

import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 09 июль 2025
 **/
public class CompanyFileProjection {
    private int id;
    private int resourcesId;
    private String fileName;
    private String extension;
    private long size;
    private String contentType;

    private LocalDate expirationDate;
    private String filedPeriod;

    private LocalDate nextUpdateDate;

    private String description;

    private CompanyFileTypeEnum type;

    private CompanyFileStatusEnum status;

    public CompanyFileProjection() {
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

    public String getFiledPeriod() {
        return filedPeriod;
    }

    public void setFiledPeriod(String filedPeriod) {
        this.filedPeriod = filedPeriod;
    }

    public LocalDate getNextUpdateDate() {
        return nextUpdateDate;
    }

    public void setNextUpdateDate(LocalDate nextUpdateDate) {
        this.nextUpdateDate = nextUpdateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyFileTypeEnum getType() {
        return type;
    }

    public void setType(CompanyFileTypeEnum type) {
        this.type = type;
    }

    public CompanyFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CompanyFileStatusEnum status) {
        this.status = status;
    }
}
