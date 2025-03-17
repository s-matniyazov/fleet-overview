package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Date;

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
    @ManyToOne(targetEntity = CityEntity.class)
    private CityEntity city;
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

    public TruckEntity() {
    }

    public TruckEntity(String unit, Date inServiceDate, String licensePlate, CityEntity city, ModelMakerEntity modelMaker,
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

    public CityEntity getCity() {
        return city;
    }

    public void setCity(CityEntity city) {
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
}
