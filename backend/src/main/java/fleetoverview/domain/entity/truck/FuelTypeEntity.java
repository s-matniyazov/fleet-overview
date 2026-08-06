package fleetoverview.domain.entity.truck;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "fuel_types")
public class FuelTypeEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public FuelTypeEntity() {
    }

    public FuelTypeEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
