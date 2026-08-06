package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.*;


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
