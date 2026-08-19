package fleetoverview.domain.entity.base;

import jakarta.persistence.*;


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
