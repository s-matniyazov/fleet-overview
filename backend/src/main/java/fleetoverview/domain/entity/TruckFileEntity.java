package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.TruckFileStatusEnum;
import fleetoverview.domain.enums.TruckFileTypeEnum;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 март 2025
 **/
@Entity
@Table(name = "truck_files")
public class TruckFileEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    private Date expirationDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TruckFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private TruckFileStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TruckEntity truck;

    public TruckFileEntity() {
    }

    public TruckFileEntity(ResourceEntity resource, Date expirationDate, String description,
                           TruckFileTypeEnum type, TruckFileStatusEnum status, TruckEntity truck) {
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

    public TruckFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TruckFileStatusEnum status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TruckFileTypeEnum getType() {
        return type;
    }

    public void setType(TruckFileTypeEnum type) {
        this.type = type;
    }

    public TruckEntity getTruck() {
        return truck;
    }
}
