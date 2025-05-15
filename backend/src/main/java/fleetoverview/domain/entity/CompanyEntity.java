package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseIdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2025
 **/
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseIdEntity {
    @Column(length = 55)
    private String name;
    @Column(length = 100)
    private String usdot;
    @Column(length = 2000)
    private String address;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String phone;
    @Column(length = 10)
    private String status;

    public CompanyEntity() {
    }

    public CompanyEntity(String name, String phone, String address,String usdot,String email,String status) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.usdot = usdot;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsdot() {
        return this.usdot;
    }

    public void setUsdot(String usdot) {
        this.usdot = usdot;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone= phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email= email;
    }
}
