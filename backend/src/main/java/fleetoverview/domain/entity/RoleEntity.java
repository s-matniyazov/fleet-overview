package fleetoverview.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Column(nullable = false, length = 55)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<ActionEntity> roleActions = new HashSet<>();

    public RoleEntity() {}

    public RoleEntity(String name, String description, Set<ActionEntity> roleActions) {
        this.name = name;
        this.description = description;
        this.roleActions = roleActions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ActionEntity> getRoleActions() {
        return roleActions;
    }

    public void setRoleActions(Set<ActionEntity> roleActions) {
        this.roleActions = roleActions;
    }
}
