package fleetoverview.domain.entity.driver;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.driver.DriverFileClassEnum;
import fleetoverview.domain.enums.driver.DriverFileStatusEnum;
import fleetoverview.domain.enums.driver.DriverFileTypeEnum;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * @author :  Sardor Matniyazov
 * @mailto :  sardorbekmatniyazov03@gmail.com
 * @created : 23 июнь 2025
 **/
@Getter
@Setter
@Entity
@Table(name = "driver_files")
public class DriverFileEntity extends BaseEntity {
    @Getter
    @OneToOne
    private ResourceEntity resource;

    private LocalDate expirationDate;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private DriverFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private DriverFileStatusEnum status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private DriverEntity driver;

    // for CDL
    private String driversLicense;
    @ManyToOne(targetEntity = StateEntity.class)
    private StateEntity state;
    @Enumerated(EnumType.STRING)
    private DriverFileClassEnum classType;
    private LocalDate issuedDate;
    @ManyToMany(targetEntity = EndorsementEntity.class, fetch = FetchType.LAZY)
    private List<EndorsementEntity> endorsement;

    // for MEDICAL_CERT

    // for MVR
    // for C/House
    private LocalDate lastCollectedOn;

    // for SSN
    private String socialSecurityNumber;

    public DriverFileEntity() {
    }

    public DriverFileEntity(ResourceEntity resource, LocalDate expirationDate, String description, DriverFileTypeEnum type, DriverFileStatusEnum status, DriverEntity driver) {
        this.resource = resource;
        this.expirationDate = expirationDate;
        this.description = description;
        this.type = type;
        this.status = status;
        this.driver = driver;
    }
}
