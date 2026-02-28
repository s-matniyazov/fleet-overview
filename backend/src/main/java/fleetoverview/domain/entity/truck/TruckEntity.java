package fleetoverview.domain.entity.truck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.*;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.enums.truck.TruckStatusEnum;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "trucks")
public class TruckEntity extends BaseEntity {
    @Column(length = 50)
    private String unit;
    private Date inServiceDate;
    @Column(length = 50)
    private String licensePlate;
    @ManyToOne(targetEntity = StateEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private StateEntity state;
    @ManyToOne(targetEntity = TruckModelMakerEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private TruckModelMakerEntity modelMaker;
    private Integer year;
    @ManyToOne(targetEntity = FuelTypeEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private FuelTypeEntity fuelType;
    private Double grossWeight;
    private Integer axles;
    @Column(length = 50)
    private String vin;
    @ManyToOne(targetEntity = OwnershipTypeEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private OwnershipTypeEntity ownershipType;

    private Boolean includeIFTA;
    @ManyToOne(targetEntity = PurchaseTypeEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private PurchaseTypeEntity purchaseType;

    @ManyToOne(targetEntity = DriverEntity.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"createdBy", "created", "truck", "state", "files", "company"})
    private DriverEntity driver;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TruckStatusEnum status = TruckStatusEnum.ACTIVE;

    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<TruckFileEntity> files = new HashSet<>();

    @OneToMany(mappedBy = "truck", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<PermitEntity> permits = new HashSet<>();

    @ManyToOne
    private CompanyEntity company;

    private String carrierResponsibleForSafety;
    private LocalDateTime statusDate;

    public TruckEntity() {
    }

    public TruckEntity(String unit, Date inServiceDate, String licensePlate, StateEntity state, TruckModelMakerEntity modelMaker,
                       Integer year, FuelTypeEntity fuelType, Double grossWeight, Integer axles, String vin,
                       OwnershipTypeEntity ownershipType, Boolean includeIFTA, PurchaseTypeEntity purchaseType,
                       DriverEntity driver, String description, CompanyEntity company, String carrierResponsibleForSafety) {
        this.unit = unit;
        this.inServiceDate = inServiceDate;
        this.licensePlate = licensePlate;
        this.state = state;
        this.modelMaker = modelMaker;
        this.year = year;
        this.fuelType = fuelType;
        this.grossWeight = grossWeight;
        this.axles = axles;
        this.vin = vin;
        this.ownershipType = ownershipType;
        this.includeIFTA = includeIFTA;
        this.purchaseType = purchaseType;
        this.driver = driver;
        this.description = description;
        this.company = company;
        this.status = TruckStatusEnum.ACTIVE;
        this.statusDate = LocalDateTime.now();
        this.carrierResponsibleForSafety = carrierResponsibleForSafety;
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

    public CompanyEntity getCompany() {
        return company;
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

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public TruckModelMakerEntity getModelMaker() {
        return modelMaker;
    }

    public void setModelMaker(TruckModelMakerEntity modelMaker) {
        this.modelMaker = modelMaker;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public FuelTypeEntity getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelTypeEntity fuelType) {
        this.fuelType = fuelType;
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

    public void setPermits(Set<PermitEntity> permits) {
        this.permits = permits;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public OwnershipTypeEntity getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(OwnershipTypeEntity ownershipType) {
        this.ownershipType = ownershipType;
    }

    public Boolean getIncludeIFTA() {
        return includeIFTA;
    }

    public void setIncludeIFTA(Boolean includeIFTA) {
        this.includeIFTA = includeIFTA;
    }

    public PurchaseTypeEntity getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseTypeEntity purchaseType) {
        this.purchaseType = purchaseType;
    }

    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity ownerOperator) {
        this.driver = ownerOperator;
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

    public void setFiles(Set<TruckFileEntity> files) {
        this.files = files;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getCarrierResponsibleForSafety() {
        return carrierResponsibleForSafety;
    }

    public void setCarrierResponsibleForSafety(String carrierResponsibleForSafety) {
        this.carrierResponsibleForSafety = carrierResponsibleForSafety;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime deActivationDate) {
        this.statusDate = deActivationDate;
    }
}
