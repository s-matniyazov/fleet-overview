package fleetoverview.domain.entity.claim;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.enums.TimeZoneEnum;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2026
 **/
@Entity
@Table(name = "collisions")
public class CollisionEntity extends BaseEntity {
    private String reportNumber;

    private LocalDate collisionDate;
    private LocalTime collisionTime;

    @Enumerated(EnumType.STRING)
    private TimeZoneEnum timeZone;

    private String city;

    @ManyToOne(targetEntity = StateEntity.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"createdBy", "created"})
    private StateEntity state;

    private String zipCode;

    private String typeOfCargo;

    @OneToMany
    private Set<ClaimEntity> claims;

    @ManyToOne
    private CompanyEntity company;

    @ManyToOne(targetEntity = TruckEntity.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"createdBy", "created", "driver", "state", "files", "company"})
    private TruckEntity truck;

    @ManyToOne(targetEntity = DriverEntity.class, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"createdBy", "created", "truck", "state", "files", "company"})
    private DriverEntity driver;

    @OneToMany(mappedBy = "collision", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<CollisionFileEntity> files = new HashSet<>();

    public CollisionEntity() {
    }

    public CollisionEntity(String reportNumber, LocalDate collisionDate, LocalTime collisionTime, TimeZoneEnum timeZone,
                           String city, StateEntity state, String zipCode, String typeOfCargo, Set<ClaimEntity> claims,
                           CompanyEntity company, TruckEntity truck, DriverEntity driver, Set<CollisionFileEntity> files) {
        this.reportNumber = reportNumber;
        this.collisionDate = collisionDate;
        this.collisionTime = collisionTime;
        this.timeZone = timeZone;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.typeOfCargo = typeOfCargo;
        this.claims = claims;
        this.company = company;
        this.truck = truck;
        this.driver = driver;
        this.files = files;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public LocalDate getCollisionDate() {
        return collisionDate;
    }

    public void setCollisionDate(LocalDate collisionDate) {
        this.collisionDate = collisionDate;
    }

    public LocalTime getCollisionTime() {
        return collisionTime;
    }

    public void setCollisionTime(LocalTime collisionTime) {
        this.collisionTime = collisionTime;
    }

    public TimeZoneEnum getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZoneEnum timeZone) {
        this.timeZone = timeZone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTypeOfCargo() {
        return typeOfCargo;
    }

    public void setTypeOfCargo(String typeOfCargo) {
        this.typeOfCargo = typeOfCargo;
    }

    public Set<ClaimEntity> getClaims() {
        return claims;
    }

    public void setClaims(Set<ClaimEntity> claims) {
        this.claims = claims;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public TruckEntity getTruck() {
        return truck;
    }

    public void setTruck(TruckEntity truck) {
        this.truck = truck;
    }

    public DriverEntity getDriver() {
        return driver;
    }

    public void setDriver(DriverEntity driver) {
        this.driver = driver;
    }

    public Set<CollisionFileEntity> getFiles() {
        return files;
    }

    public void setFiles(Set<CollisionFileEntity> files) {
        this.files = files;
    }
}
