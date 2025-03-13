package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "model_makers")
public class ModelMakerEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public ModelMakerEntity() {
    }

    public ModelMakerEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
