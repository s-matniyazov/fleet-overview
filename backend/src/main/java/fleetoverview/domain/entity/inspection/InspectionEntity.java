package fleetoverview.domain.entity.inspection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inspection")
public class InspectionEntity extends BaseEntity {
    @Column
    private String inspection_number;

    @Column
    private LocalDate inspection_date;

    @ManyToOne(targetEntity = CompanyEntity.class)
    private CompanyEntity company;

    @ManyToOne(targetEntity = DriverEntity.class)
    private DriverEntity driver;

    @ManyToOne(targetEntity = DriverEntity.class)
    private DriverEntity coDriver;

    @Column(length = 500)
    private String description;

    @ManyToOne(targetEntity = StateEntity.class)
    @JsonIgnoreProperties({"createdBy", "created"})
    private StateEntity state;

    @Column(length = 100)
    private String city;

    @Column(length = 500)
    private String location;
    @Column
    private boolean violation_discovered;

    @OneToMany(mappedBy = "inspection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"createdBy", "created"})
    private Set<InspectionFileEntity> files = new HashSet<>();

}
