package fleetoverview.domain.projection.trailer;

import fleetoverview.domain.enums.truck.TruckStatusEnum;
import fleetoverview.domain.projection.truck.TruckFileProjection;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 30 июнь 2025
 **/
public class TrailerProjection {
    private Integer id;
    private String unit;
    private String licensePlate;
    private Date inServiceDate;

    private Integer modelMakerId;
    private String modelMakerName;

    private Integer typeId;
    private String typeName;

    private Integer year;

    private Integer axles;
    private Double length;
    private Double height;
    private String vin;

    private Integer ownershipTypeId;
    private String ownershipTypeName;

    private Integer purchaseTypeId;
    private String purchaseTypeName;

    private Integer driverId;
    private String driverName;

    private String description;

    private TruckStatusEnum status;

    private Set<TruckFileProjection> files;

    private Integer companyId;
    private String companyName;

    private Integer createdById;
    private String createdByName;
    private LocalDate created;

    public TrailerProjection() {
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Date getInServiceDate() {
        return inServiceDate;
    }

    public void setInServiceDate(Date inServiceDate) {
        this.inServiceDate = inServiceDate;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAxles() {
        return axles;
    }

    public void setAxles(Integer axles) {
        this.axles = axles;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
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

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }
}
