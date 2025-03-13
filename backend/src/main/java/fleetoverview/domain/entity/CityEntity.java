package fleetoverview.domain.entity;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 13 март 2025
 **/
@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity {
    @Column(length = 50)
    private String name;
    @ManyToOne(targetEntity = CountryEntity.class)
    private CountryEntity country;

    public CityEntity() {
    }

    public CityEntity(String name, CountryEntity country) {
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
