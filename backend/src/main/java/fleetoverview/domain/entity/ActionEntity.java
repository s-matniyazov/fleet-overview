package fleetoverview.domain.entity;

import org.springframework.security.core.GrantedAuthority;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.ActionTypesEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

/**
 * @author :  sardor.matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 28 янв. 2025
 **/

@Entity
@Table(name = "actions")
public class ActionEntity extends BaseEntity implements GrantedAuthority {
    private String name;
    private String description;
    private String url;

    @Enumerated(EnumType.STRING)
    private ActionTypesEnum type;

    public ActionEntity() {
    }

    public ActionEntity(String name, String description, String url, ActionTypesEnum type) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.type = type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ActionTypesEnum getType() {
        return type;
    }

    public void setType(ActionTypesEnum type) {
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
