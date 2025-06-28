package fleetoverview.domain.entity.base;

import fleetoverview.domain.entity.UserEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity createdBy;
    @UpdateTimestamp
    private Date updated;

    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity updatedBy;

    public BaseEntity(Date created, UserEntity createdBy, Date updated, UserEntity updatedBy) {
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

    public UserEntity getCreatedBy() {
        return createdBy;
    }
}
