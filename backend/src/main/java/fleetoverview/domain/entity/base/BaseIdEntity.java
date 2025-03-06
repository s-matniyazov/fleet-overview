package fleetoverview.domain.entity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 27 Jan. 2025
 **/
@MappedSuperclass
public abstract class BaseIdEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Id
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
