package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "ownership_types")
public class OwnershipTypeEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public OwnershipTypeEntity() {
    }

    public OwnershipTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
