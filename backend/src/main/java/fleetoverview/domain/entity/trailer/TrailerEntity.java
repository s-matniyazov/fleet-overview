package fleetoverview.domain.entity.trailer;

import fleetoverview.domain.entity.*;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.trailer.TrailerStatusEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
@Entity
@Table(name = "trailers")
public class TrailerEntity extends BaseEntity {
    @Column(length = 50)
    private String unit;
    @Column(length = 50)
    private String licensePlate;
    private Date inServiceDate;
    @ManyToOne(targetEntity = TrailerModelMakerEntity.class)
    private TrailerModelMakerEntity modelMaker;
    @ManyToOne(targetEntity = TrailerTypeEntity.class)
    private TrailerTypeEntity type;
    private Integer year;
    private Integer axles;
    private Double length;
    private Double height;
    @Column(length = 50)
    private String vin;
    @ManyToOne(targetEntity = OwnershipTypeEntity.class)
    private OwnershipTypeEntity ownershipType;

    @ManyToOne(targetEntity = PurchaseTypeEntity.class)
    private PurchaseTypeEntity purchaseType;

    @ManyToOne(targetEntity = DriverEntity.class)
    private DriverEntity driver;

    @Column(length = 4000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TrailerStatusEnum status = TrailerStatusEnum.ACTIVE;

    @OneToMany(mappedBy = "trailer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TrailerFileEntity> files = new HashSet<>();

    @OneToMany(mappedBy = "trailer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<PermitEntity> permits = new HashSet<>();

    @ManyToOne
    private CompanyEntity company;

    public TrailerEntity() {}

    public TrailerEntity(String unit, String licensePlate, Date inServiceDate, TrailerModelMakerEntity modelMaker,
                         TrailerTypeEntity type, Integer year, Integer axles, Double length, Double height, String vin,
                         OwnershipTypeEntity ownershipType, PurchaseTypeEntity purchaseType, DriverEntity driver,
                         String description, CompanyEntity company) {
        this.unit = unit;
        this.licensePlate = licensePlate;
        this.inServiceDate = inServiceDate;
        this.modelMaker = modelMaker;
        this.type = type;
        this.year = year;
        this.axles = axles;
        this.length = length;
        this.height = height;
        this.vin = vin;
        this.ownershipType = ownershipType;
        this.purchaseType = purchaseType;
        this.driver = driver;
        this.description = description;
        this.status = TrailerStatusEnum.ACTIVE;
        this.company = company;
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

    public TrailerModelMakerEntity getModelMaker() {
        return modelMaker;
    }

    public void setModelMaker(TrailerModelMakerEntity modelMaker) {
        this.modelMaker = modelMaker;
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

    public TrailerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TrailerStatusEnum status) {
        this.status = status;
    }

    public Set<TrailerFileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<TrailerFileEntity> files) {
        this.files = files;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public TrailerTypeEntity getType() {
        return type;
    }

    public void setType(TrailerTypeEntity type) {
        this.type = type;
    }

    public Double getLength() {
        return length;
    }

    public Set<PermitEntity> getPermits() {
        return permits;
    }

    public void setPermits(Set<PermitEntity> permits) {
        this.permits = permits;
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
}
