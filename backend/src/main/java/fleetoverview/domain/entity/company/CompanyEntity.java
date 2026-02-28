package fleetoverview.domain.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.TimeZoneEnum;
import fleetoverview.domain.enums.company.CompanyEntrantStatusEnum;
import fleetoverview.domain.enums.company.CompanyStatusEnum;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2025
 **/
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseEntity {
    @Column(length = 500)
    private String name;
    @Column(length = 500)
    private String ownerName;
    @Column(length = 20)
    private String dot;
    @Column(length = 20)
    private String pinNumber;
    @Column(length = 20)
    private String mc;
    @Column(length = 500)
    private String dba;
    @Column(length = 500)
    private String fein;
    @ManyToOne(targetEntity = StateEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private StateEntity state;
    @Column(length = 2000)
    private String address1;
    @Column(length = 2000)
    private String address2;
    @Column(length = 200)
    private String city;
    @Column(length = 20)
    private String zipcode;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String phone;

    @Enumerated(EnumType.STRING)
    private TimeZoneEnum timeZone;

    @Enumerated(EnumType.STRING)
    private CompanyStatusEnum status;

    @Enumerated(EnumType.STRING)
    private CompanyEntrantStatusEnum entrantStatus;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<CompanyFileEntity> files = new HashSet<>();

    public CompanyEntity() {
    }

    public CompanyEntity(String name, String ownerName, String dot, String pinNumber, String mc, String dba, String fein,
                         StateEntity state, String address1, String address2, String city,
                         String zipcode, String email, String phone, TimeZoneEnum timeZone, CompanyStatusEnum status
            , CompanyEntrantStatusEnum entrantStatus) {
        this.name = name;
        this.ownerName = ownerName;
        this.dot = dot;
        this.pinNumber = pinNumber;
        this.mc = mc;
        this.dba = dba;
        this.fein = fein;
        this.state = state;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.zipcode = zipcode;
        this.email = email;
        this.phone = phone;
        this.timeZone = timeZone;
        this.status = status;
        this.entrantStatus = entrantStatus;
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

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
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

    public void setFiles(Set<CompanyFileEntity> files) {
        this.files = files;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    public CompanyEntrantStatusEnum getEntrantStatus() {
        return entrantStatus;
    }

    public void setEntrantStatus(CompanyEntrantStatusEnum entrantStatus) {
        this.entrantStatus = entrantStatus;
    }
}
