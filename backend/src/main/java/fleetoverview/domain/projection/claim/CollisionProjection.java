package fleetoverview.domain.projection.claim;

import fleetoverview.domain.enums.TimeZoneEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class CollisionProjection {
    private Integer id;
    private String reportNumber;
    private LocalDate collisionDate;
    private LocalTime collisionTime;
    private TimeZoneEnum timeZone;
    private String city;
    private Integer stateId;
    private String stateName;
    private String zipCode;
    private String typeOfCargo;

    private Integer companyId;
    private String companyName;

    private Integer truckId;
    private String truckUnit;

    private Integer driverId;
    private String driverName;

    private Integer createdById;
    private String createdByName;
    private LocalDate created;

    private Set<CollisionFileProjection> files;

    public CollisionProjection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getTruckId() {
        return truckId;
    }

    public void setTruckId(Integer truckId) {
        this.truckId = truckId;
    }

    public String getTruckUnit() {
        return truckUnit;
    }

    public void setTruckUnit(String truckUnit) {
        this.truckUnit = truckUnit;
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

    public Set<CollisionFileProjection> getFiles() {
        return files;
    }

    public void setFiles(Set<CollisionFileProjection> files) {
        this.files = files;
    }
}
