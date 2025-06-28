package fleetoverview.domain.entity.driver;

import fleetoverview.domain.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
@Entity
@Table(name = "endorsements")
public class EndorsementEntity  extends BaseEntity {
    @Column(length = 50)
    private String name;

    public EndorsementEntity() {
    }

    public EndorsementEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
