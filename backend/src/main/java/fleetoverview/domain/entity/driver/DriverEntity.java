package fleetoverview.domain.entity.driver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.DriverStatusEnum;
import fleetoverview.domain.enums.driver.DriverTypeEnum;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class DriverEntity extends BaseEntity {
    @ManyToOne(targetEntity = CompanyEntity.class)
    private CompanyEntity company;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;
    @Column(length = 100)
    private String middleName;
    @Column
    private LocalDate hireDate;
    @Column
    private LocalDateTime terminationDate;
    @Column(length = 500)
    private String terminationReason;
    @Column
    private LocalDate dateOfBirth;
    @ManyToOne(targetEntity = StateEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private StateEntity state;

    @Column(length = 500)
    private String address;

    @Column(length = 100)
    private String city;
    @Column(length = 20)
    private String zipCode;
    @Column(length = 100)
    private String email;
    @Column(length = 50)
    private String phone;
    @Enumerated(EnumType.STRING)
    private DriverStatusEnum status = DriverStatusEnum.ACTIVE;
    @Enumerated(EnumType.STRING)
    private DriverTypeEnum type;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<DriverFileEntity> files = new HashSet<>();

    public DriverEntity(CompanyEntity company,String firstName, String lastName, String middleName, LocalDate hireDate,
                        LocalDateTime terminationDate, LocalDate dateOfBirth, StateEntity state, String address, String city, String zipCode,
                        String email, String phone, DriverStatusEnum status, DriverTypeEnum type) {
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.state = state;
        this.type = type;
    }

    public DriverEntity() {}

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

    public DriverStatusEnum getStatus() {
        return status;
    }

    public String getTerminationReason() {
        return terminationReason;
    }

    public void setTerminationReason(String terminationReason) {
        this.terminationReason = terminationReason;
    }

    public void setStatus(DriverStatusEnum status) {
        this.status = status;
    }

    public DriverTypeEnum getType() {
        return type;
    }

    public void setType(DriverTypeEnum type) {
        this.type = type;
    }

    public void setFiles(Set<DriverFileEntity> files) {
        this.files = files;
    }
}
