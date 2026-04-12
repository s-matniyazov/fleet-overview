package fleetoverview.domain.entity.driver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.driver.DriverFileClassEnum;
import fleetoverview.domain.enums.driver.DriverFileStatusEnum;
import fleetoverview.domain.enums.driver.DriverFileTypeEnum;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
@Entity
@Table(name = "driver_files")
public class DriverFileEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    private LocalDate expirationDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private DriverFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private DriverFileStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private DriverEntity driver;

    // for CDL
    private String driversLicense;
    @ManyToOne(targetEntity = StateEntity.class)
    private StateEntity state;
    @Enumerated(EnumType.STRING)
    private DriverFileClassEnum classType;
    private LocalDate issuedDate;
    @JsonIgnore
    @ManyToMany(targetEntity = EndorsementEntity.class,fetch = FetchType.LAZY)
    private List<EndorsementEntity> endorsement;

    // for MEDICAL_CERT

    // for MVR
    // for C/House
    private LocalDate lastCollectedOn;

    // for SSN
    private String socialSecurityNumber;

    public DriverFileEntity() {
    }

    public DriverFileEntity(ResourceEntity resource, LocalDate expirationDate, String description, DriverFileTypeEnum type, DriverFileStatusEnum status, DriverEntity driver) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.driver = driver;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
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

    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }

    public String getDriversLicense() {
        return driversLicense;
    }

    public void setDriversLicense(String driversLicense) {
        this.driversLicense = driversLicense;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
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

    public LocalDate getLastCollectedOn() {
        return lastCollectedOn;
    }

    public void setLastCollectedOn(LocalDate lastCollectedOn) {
        this.lastCollectedOn = lastCollectedOn;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public List<EndorsementEntity> getEndorsement() {
        return endorsement;
    }

    public void setEndorsement(List<EndorsementEntity> endorsement) {
        this.endorsement = endorsement;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }
}
