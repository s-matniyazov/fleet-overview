package fleetoverview.domain.entity.base;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.util.Date;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 27 янв. 2025
 **/
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity extends BaseIdEntity {
    @CreationTimestamp
    private Date created;
    @CreatedBy
    private Integer createdBy;
    @UpdateTimestamp
    private Date updated;

    @LastModifiedBy
    private Integer updatedBy;

    public BaseEntity(Date created, Integer createdBy, Date updated, Integer updatedBy) {
        this.created = created;
        this.createdBy = createdBy;
        this.updated = updated;
        this.updatedBy = updatedBy;
    }

    public BaseEntity() {
    }

    public Date getCreated() {
        return created;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }
}
