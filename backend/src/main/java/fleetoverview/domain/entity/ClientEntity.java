package fleetoverview.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.ClientStatusEnum;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 10 февр. 2025
 **/
@Entity
@Table(name = "clients")
public class ClientEntity extends BaseEntity {
    @Column(nullable = false, length = 55)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 20)
    private String shortName;

    @Column(length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private ClientStatusEnum status = ClientStatusEnum.A;

    public ClientEntity() {}

    public ClientEntity(String name, String description, String shortName, String phone) {
        this.name = name;
        this.description = description;
        this.shortName = shortName;
        this.phone = phone;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ClientStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ClientStatusEnum status) {
        this.status = status;
    }
}
