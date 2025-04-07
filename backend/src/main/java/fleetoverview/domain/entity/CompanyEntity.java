package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseIdEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2025
 **/
@Entity
@Table(name = "companies")
public class CompanyEntity extends BaseIdEntity {
    private String name;
    private String properties;
}
