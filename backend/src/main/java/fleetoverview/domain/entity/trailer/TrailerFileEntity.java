package fleetoverview.domain.entity.trailer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.trailer.TrailerFileStatusEnum;
import fleetoverview.domain.enums.trailer.TrailerFileTypeEnum;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
@Entity
@Table(name = "truck_files")
public class TrailerFileEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    private Date expirationDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private TrailerFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private TrailerFileStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TrailerEntity trailer;

    public TrailerFileEntity() {
    }

    public TrailerFileEntity(ResourceEntity resource, Date expirationDate, String description,
                           TrailerFileTypeEnum type, TrailerFileStatusEnum status, TrailerEntity trailer) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.trailer = trailer;
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

    public TrailerFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TrailerFileStatusEnum status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TrailerFileTypeEnum getType() {
        return type;
    }

    public void setType(TrailerFileTypeEnum type) {
        this.type = type;
    }

    public TrailerEntity getTrailer() {
        return trailer;
    }
}
