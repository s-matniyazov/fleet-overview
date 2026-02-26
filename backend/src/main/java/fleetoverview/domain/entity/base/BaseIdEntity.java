package fleetoverview.domain.entity.base;

import jakarta.persistence.*;

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
