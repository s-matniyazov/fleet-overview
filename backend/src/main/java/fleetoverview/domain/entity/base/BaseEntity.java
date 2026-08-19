package fleetoverview.domain.entity.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseIdEntity {
    @CreationTimestamp
    private Date created;
    @CreatedBy
    private Integer createdById;
    @UpdateTimestamp
    private Date updated;

    @LastModifiedBy
    private Integer updatedById;

    public BaseEntity(Date created, Integer createdById, Date updated, Integer updatedById) {
        this.created = created;
        this.createdById = createdById;
        this.updated = updated;
        this.updatedById = updatedById;
    }

    public BaseEntity() {
    }

    public Date getCreated() {
        return created;
    }

    public Integer getCreatedById() {
        return createdById;
    }
}
