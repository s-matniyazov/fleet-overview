package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.enums.TruckFileTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private TruckEntity truck;

    public TruckFileEntity() {
    }

    public TruckFileEntity(ResourceEntity resource, Date expirationDate, String description, TruckFileTypeEnum type, TruckEntity truck) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.description = description;
        this.type = type;
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

    public void setTruck(TruckEntity truck) {
        this.truck = truck;
    }

    public TruckFileTypeEnum getType() {
        return type;
    }

    public void setType(TruckFileTypeEnum type) {
        this.type = type;
    }
}
