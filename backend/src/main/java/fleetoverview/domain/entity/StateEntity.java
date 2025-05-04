package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.*;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "states", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "country_id" }) })
public class StateEntity extends BaseEntity {
    @Column(length = 50)
    private String name;
    @ManyToOne(targetEntity = CountryEntity.class)
    private CountryEntity country;

    public StateEntity() {
    }

    public StateEntity(String name, CountryEntity country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}
