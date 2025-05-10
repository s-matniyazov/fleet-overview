package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "purchase_types")
public class PurchaseTypeEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public PurchaseTypeEntity() {
    }

    public PurchaseTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
