package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.enums.MenuStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity(name = "ts_menu")
public class MenuEntity extends BaseEntity {

    @Column(name="name")
    private String name;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private MenuStatusEnum status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "parentId",referencedColumnName = "id")
    private List<MenuEntity> subMenuEntities;

    public MenuEntity(String name, MenuStatusEnum status, List<MenuEntity> subMenuEntities) {
        this.name = name;
        this.status = status;
        this.subMenuEntities = subMenuEntities;
    }

    public MenuEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MenuStatusEnum status) {
        this.status = status;
    }

    public List<MenuEntity> getSubMenuEntities() {
        return subMenuEntities;
    }

    public void setSubMenuEntities(List<MenuEntity> subMenuEntities) {
        this.subMenuEntities = subMenuEntities;
    }
}
