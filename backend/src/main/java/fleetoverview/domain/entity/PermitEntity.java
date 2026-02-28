package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.enums.PermitStatusEnum;
import fleetoverview.domain.enums.PermitTypeEnum;

import jakarta.persistence.*;
import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 июнь 2025
 **/
@Entity
@Table(name = "permits")
public class PermitEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    private Date expirationDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private PermitTypeEnum type;

    @Enumerated(EnumType.STRING)
    private PermitStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TruckEntity truck;

    public PermitEntity() {
    }

    public PermitEntity(ResourceEntity resource, Date expirationDate, String description, PermitTypeEnum type, PermitStatusEnum status, TruckEntity truck) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.truck = truck;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermitTypeEnum getType() {
        return type;
    }

    public void setType(PermitTypeEnum type) {
        this.type = type;
    }

    public PermitStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PermitStatusEnum status) {
        this.status = status;
    }

    public TruckEntity getTruck() {
        return truck;
    }

    public void setTruck(TruckEntity truck) {
        this.truck = truck;
    }
}
