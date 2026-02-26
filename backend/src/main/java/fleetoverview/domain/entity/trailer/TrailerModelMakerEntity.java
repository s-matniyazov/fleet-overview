package fleetoverview.domain.entity.trailer;

import fleetoverview.domain.entity.base.BaseEntity;

import jakarta.persistence.*;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 24 май 2025
 **/
@Entity
@Table(name = "trailer_model_makers")
public class TrailerModelMakerEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    public TrailerModelMakerEntity() {
    }

    public TrailerModelMakerEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
