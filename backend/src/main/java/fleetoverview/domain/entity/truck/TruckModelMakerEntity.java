package fleetoverview.domain.entity.truck;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "truck_model_makers")
public class TruckModelMakerEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public TruckModelMakerEntity() {
    }

    public TruckModelMakerEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
