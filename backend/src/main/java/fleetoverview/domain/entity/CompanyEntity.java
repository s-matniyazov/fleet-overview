package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
    @Column(length = 4000)
    private String description;
    @Column(length = 2000)
    private String address;

    public CompanyEntity() {
    }

    public CompanyEntity(String name, String description, String address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String properties) {
        this.description = properties;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
