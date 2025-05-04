package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.enums.TruckStatusEnum;
import fleetoverview.domain.listener.TruckListener;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "trucks")
@EntityListeners(TruckListener.class)
public class TruckEntity extends BaseEntity {
    @Column(length = 50)
    private String unit;
    private Date inServiceDate;
    @Column(length = 50)
    private String licensePlate;
    @ManyToOne(targetEntity = StateEntity.class)
    private StateEntity city;
    @ManyToOne(targetEntity = ModelMakerEntity.class)
    private ModelMakerEntity modelMaker;
    private Integer year;
    @ManyToOne(targetEntity = FuelTypeEntity.class)
    private FuelTypeEntity fuelType;
    private Double grossWeight;
    private Integer axles;
    @Column(length = 50)
    private String vin;
    @ManyToOne(targetEntity = OwnershipTypeEntity.class)
    private OwnershipTypeEntity ownershipType;

    private Boolean includeIFTA;
    @ManyToOne(targetEntity = PurchaseTypeEntity.class)
    private PurchaseTypeEntity purchaseType;

    @ManyToOne(targetEntity = OwnerOperatorEntity.class)
    private OwnerOperatorEntity ownerOperator;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TruckStatusEnum status = TruckStatusEnum.ACTIVE;

    @OneToMany(mappedBy = "truck", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TruckFileEntity> files = new HashSet<>();

    public TruckEntity() {
    }

    public TruckEntity(String unit, Date inServiceDate, String licensePlate, StateEntity city, ModelMakerEntity modelMaker,
                       Integer year, FuelTypeEntity fuelType, Double grossWeight, Integer axles, String vin,
                       OwnershipTypeEntity ownershipType, Boolean includeIFTA, PurchaseTypeEntity purchaseType,
                       OwnerOperatorEntity ownerOperator, String description) {
        this.unit = unit;
        this.inServiceDate = inServiceDate;
        this.licensePlate = licensePlate;
        this.city = city;
        this.modelMaker = modelMaker;
        this.year = year;
        this.fuelType = fuelType;
        this.grossWeight = grossWeight;
        this.axles = axles;
        this.vin = vin;
        this.ownershipType = ownershipType;
        this.includeIFTA = includeIFTA;
        this.purchaseType = purchaseType;
        this.ownerOperator = ownerOperator;
        this.description = description;
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

    public StateEntity getCity() {
        return city;
    }

    public void setCity(StateEntity city) {
        this.city = city;
    }

    public ModelMakerEntity getModelMaker() {
        return modelMaker;
    }

    public void setModelMaker(ModelMakerEntity modelMaker) {
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

    public OwnerOperatorEntity getOwnerOperator() {
        return ownerOperator;
    }

    public void setOwnerOperator(OwnerOperatorEntity ownerOperator) {
        this.ownerOperator = ownerOperator;
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

    public Set<TruckFileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<TruckFileEntity> files) {
        this.files = files;
    }
}
