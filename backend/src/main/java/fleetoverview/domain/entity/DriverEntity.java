package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.enums.DriverStatusEnum;

import javax.persistence.*;
import java.sql.Date;


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
    private Date hireDate;
    @Column
    private Date dateOfBirth;
    @ManyToOne(targetEntity = StateEntity.class)
    private StateEntity state;

    @Column(length = 500)
    private String address;

    @Column(length = 100)
    private String city;
    @Column(length = 100)
    private int zipCode;
    @Column(length = 100)
    private String email;
    @Column(length = 50)
    private String phone;
    @Enumerated(EnumType.STRING)
    private DriverStatusEnum status = DriverStatusEnum.ACTIVE;


    public DriverEntity(CompanyEntity company,String firstName, String lastName, String middleName, Date hireDate,
                        Date dateOfBirth, StateEntity state, String address,
                        String city, int zipCode, String email, String phone, DriverStatusEnum status) {
        this.company = company;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.hireDate = hireDate;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.email = email;
        this.phone = phone;
        this.status = status;
    }

    public DriverEntity() {

    }
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

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
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

    public void setStatus(DriverStatusEnum status) {
        this.status = status;
    }
}
