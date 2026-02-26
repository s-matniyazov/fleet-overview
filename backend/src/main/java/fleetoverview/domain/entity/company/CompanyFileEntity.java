package fleetoverview.domain.entity.company;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.company.CompanyFileStatusEnum;
import fleetoverview.domain.enums.company.CompanyFileTypeEnum;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 06 июль 2025
 **/
@Entity
@Table(name = "company_files")
public class CompanyFileEntity extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    private LocalDate expirationDate;
    private String filedPeriod;

    private LocalDate nextUpdateDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private CompanyFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private CompanyFileStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private CompanyEntity company;

    public CompanyFileEntity() {
    }

    public CompanyFileEntity(ResourceEntity resource, LocalDate expirationDate, String filedPeriod,
                             LocalDate nextUpdateDate, String description, CompanyFileTypeEnum type,
                             CompanyFileStatusEnum status, CompanyEntity company) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.filedPeriod = filedPeriod;
        this.nextUpdateDate = nextUpdateDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.company = company;
    }

    public ResourceEntity getResource() {
        return resource;
    }

    public void setResource(ResourceEntity resource) {
        this.resource = resource;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getFiledPeriod() {
        return filedPeriod;
    }

    public void setFiledPeriod(String filedPeriod) {
        this.filedPeriod = filedPeriod;
    }

    public LocalDate getNextUpdateDate() {
        return nextUpdateDate;
    }

    public void setNextUpdateDate(LocalDate nextUpdateDate) {
        this.nextUpdateDate = nextUpdateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyFileTypeEnum getType() {
        return type;
    }

    public void setType(CompanyFileTypeEnum type) {
        this.type = type;
    }

    public CompanyFileStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CompanyFileStatusEnum status) {
        this.status = status;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }
}
