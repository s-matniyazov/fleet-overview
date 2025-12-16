package fleetoverview.domain.projection.company;

import fleetoverview.domain.enums.TimeZoneEnum;
import fleetoverview.domain.enums.company.CompanyEntrantStatusEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;
import fleetoverview.domain.projection.company.CompanyFileProjection;

import java.time.LocalDate;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 09 июль 2025
 **/
public class CompanyProjection {
    Integer id;
    private String name;
    private String ownerName;
    private String dot;
    private String mc;
    private String dba;
    private String fein;
    private Integer stateId;
    private String stateName;
    private Integer countryId;
    private String countryName;
    private String address1;
    private String address2;
    private String city;
    private String zipcode;
    private String email;
    private String phone;
    private String pinNumber;
    private TimeZoneEnum timeZone;
    private CompanyStatusEnum status;
    private CompanyEntrantStatusEnum entrantStatus;

    private Integer createdById;
    private String createdByName;
    private LocalDate created;

    private Set<CompanyFileProjection> files;

    public CompanyProjection() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDot() {
        return dot;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getDba() {
        return dba;
    }

    public void setDba(String dba) {
        this.dba = dba;
    }

    public String getFein() {
        return fein;
    }

    public void setFein(String fein) {
        this.fein = fein;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TimeZoneEnum getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZoneEnum timeZone) {
        this.timeZone = timeZone;
    }

    public CompanyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CompanyStatusEnum status) {
        this.status = status;
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

    public Set<CompanyFileProjection> getFiles() {
        return files;
    }

    public void setFiles(Set<CompanyFileProjection> files) {
        this.files = files;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public CompanyEntrantStatusEnum getEntrantStatus() {
        return entrantStatus;
    }

    public void setEntrantStatus(CompanyEntrantStatusEnum entrantStatus) {
        this.entrantStatus = entrantStatus;
    }
}
