package fleetoverview.domain.entity.base;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 27 янв. 2025
 **/
@MappedSuperclass
public abstract class BaseEntity extends BaseIdEntity {
    @CreationTimestamp
    private Date created;
    private String createdBy;
    @UpdateTimestamp
    private Date updated;
    private String updatedBy;

    public BaseEntity(Date created, String createdBy, Date updated, String updatedBy) {
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

    public String getCreatedBy() {
        return createdBy;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
}
