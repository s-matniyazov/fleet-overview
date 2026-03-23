package fleetoverview.domain.entity.claim;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.claim.CollisionFileStatusEnum;
import fleetoverview.domain.enums.claim.CollisionFileTypeEnum;
import jakarta.persistence.*;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2026
 **/
@Entity
@Table(name = "collision_files")
public class CollisionFileEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;
    @Column(length = 2000)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private CollisionEntity collision;

    @Enumerated(EnumType.STRING)
    private CollisionFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private CollisionFileStatusEnum status;

    public CollisionFileEntity() {
    }

    public CollisionFileEntity(String description, CollisionEntity collision, CollisionFileTypeEnum type, CollisionFileStatusEnum status) {
        this.description = description;
        this.collision = collision;
        this.type = type;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CollisionEntity getCollision() {
        return collision;
    }

    public void setCollision(CollisionEntity collision) {
        this.collision = collision;
    }

    public CollisionFileTypeEnum getType() {
        return type;
    }

    public void setType(CollisionFileTypeEnum type) {
        this.type = type;
    }

    public CollisionFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CollisionFileStatusEnum status) {
        this.status = status;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }
}
