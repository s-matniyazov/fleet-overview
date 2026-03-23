package fleetoverview.domain.entity.claim;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 05 апр. 2026
 **/
@Entity
@Table(name = "claims")
public class ClaimEntity extends BaseEntity {
    @Column(length = 500)
    private String description;
    private String claim;
    private BigDecimal price;

    public ClaimEntity() {
    }

    public ClaimEntity(String description, String claim, BigDecimal price) {
        this.description = description;
        this.claim = claim;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
