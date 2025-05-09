package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "owner_operators")
public class OwnerOperatorEntity extends BaseEntity {
    @Column(length = 50)
    private String name;

    @ManyToOne
    private CompanyEntity company;

    public OwnerOperatorEntity() {
    }

    public OwnerOperatorEntity(String name, CompanyEntity company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
