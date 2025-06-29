package fleetoverview.domain.projection.truck;

import fleetoverview.domain.enums.truck.TruckStatusEnum;
import fleetoverview.domain.projection.PermitProjection;

import java.sql.Date;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 29 июнь 2025
 **/
public class TruckProjection {
    private Integer id;
    private String unit;
    private Date inServiceDate;
    private String licensePlate;

    private Integer stateId;
    private String stateName;

    private Integer countryId;
    private String countryName;

    private Integer modelMakerId;
    private String modelMakerName;

    private Integer year;

    private Integer fuelTypeId;
    private String fuelTypeName;

    private Double grossWeight;
    private Integer axles;
    private String vin;

    private Integer ownershipTypeId;
    private String ownershipTypeName;

    private Boolean includeIFTA;

    private Integer purchaseTypeId;
    private String purchaseTypeName;

    private Integer driverId;
    private String driverName;

    private String description;

    private TruckStatusEnum status;

    private Set<TruckFileProjection> files;

    private Set<PermitProjection> permits;

    private Integer companyId;
    private String companyName;

    private Integer createdById;
    private String createdByName;
    private Date created;

    public TruckProjection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getInServiceDate() {
        return inServiceDate;
    }

    public void setInServiceDate(Date inServiceDate) {
        this.inServiceDate = inServiceDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getModelMakerId() {
        return modelMakerId;
    }

    public void setModelMakerId(Integer modelMakerId) {
        this.modelMakerId = modelMakerId;
    }

    public String getModelMakerName() {
        return modelMakerName;
    }

    public void setModelMakerName(String modelMakerName) {
        this.modelMakerName = modelMakerName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getFuelTypeName() {
        return fuelTypeName;
    }

    public void setFuelTypeName(String fuelTypeName) {
        this.fuelTypeName = fuelTypeName;
    }

    public Double getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Double grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Integer getAxles() {
        return axles;
    }

    public void setAxles(Integer axles) {
        this.axles = axles;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getOwnershipTypeId() {
        return ownershipTypeId;
    }

    public void setOwnershipTypeId(Integer ownershipTypeId) {
        this.ownershipTypeId = ownershipTypeId;
    }

    public String getOwnershipTypeName() {
        return ownershipTypeName;
    }

    public void setOwnershipTypeName(String ownershipTypeName) {
        this.ownershipTypeName = ownershipTypeName;
    }

    public Boolean getIncludeIFTA() {
        return includeIFTA;
    }

    public void setIncludeIFTA(Boolean includeIFTA) {
        this.includeIFTA = includeIFTA;
    }

    public Integer getPurchaseTypeId() {
        return purchaseTypeId;
    }

    public void setPurchaseTypeId(Integer purchaseTypeId) {
        this.purchaseTypeId = purchaseTypeId;
    }

    public String getPurchaseTypeName() {
        return purchaseTypeName;
    }

    public void setPurchaseTypeName(String purchaseTypeName) {
        this.purchaseTypeName = purchaseTypeName;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TruckStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TruckStatusEnum status) {
        this.status = status;
    }

    public Set<TruckFileProjection> getFiles() {
        return files;
    }

    public void setFiles(Set<TruckFileProjection> files) {
        this.files = files;
    }

    public Set<PermitProjection> getPermits() {
        return permits;
    }

    public void setPermits(Set<PermitProjection> permits) {
        this.permits = permits;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
