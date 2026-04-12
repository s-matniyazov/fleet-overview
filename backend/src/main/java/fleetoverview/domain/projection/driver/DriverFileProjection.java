package fleetoverview.domain.projection.driver;

import fleetoverview.domain.entity.driver.EndorsementEntity;
import fleetoverview.domain.enums.driver.DriverFileClassEnum;
import fleetoverview.domain.enums.driver.DriverFileStatusEnum;
import fleetoverview.domain.enums.driver.DriverFileTypeEnum;

import java.time.LocalDate;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public class DriverFileProjection {
    private int id;
    private int resourcesId;
    private String fileName;
    private String extension;
    private long size;
    private String contentType;

    private LocalDate expirationDate;

    private String description;

    private DriverFileTypeEnum type;

    private DriverFileStatusEnum status;

    // for CDL
    private String driversLicense;
    private Integer stateId;
    private String stateName;
    private Integer countryId;
    private String countryName;
    private DriverFileClassEnum classType;
    private LocalDate issuedDate;
    private List<EndorsementEntity> endorsement;

    // for MEDICAL_CERT

    // for MVR
    // for C/House
    private LocalDate lastCollectedOn;

    // for C/House
    private String socialSecurityNumber;

    public DriverFileProjection() {
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

    public DriverFileTypeEnum getType() {
        return type;
    }

    public void setType(DriverFileTypeEnum type) {
        this.type = type;
    }

    public DriverFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DriverFileStatusEnum status) {
        this.status = status;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public DriverFileClassEnum getClassType() {
        return classType;
    }

    public void setClassType(DriverFileClassEnum classType) {
        this.classType = classType;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public List<EndorsementEntity> getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(List<EndorsementEntity> endorsement) {
        this.endorsement = endorsement;
    }

    public LocalDate getLastCollectedOn() {
        return lastCollectedOn;
    }

    public void setLastCollectedOn(LocalDate lastCollectedOn) {
        this.lastCollectedOn = lastCollectedOn;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
