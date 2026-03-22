package fleetoverview.domain.entity.inspection;

import fleetoverview.domain.entity.StateEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.entity.company.CompanyEntity;
import fleetoverview.domain.entity.driver.DriverEntity;
import fleetoverview.domain.entity.truck.TruckEntity;
import fleetoverview.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "inspection")
public class Inspection extends BaseEntity {
    @Column
    private String inspectionNumber;

    @Column
    private LocalDate inspectionDate;

    @ManyToOne(targetEntity = CompanyEntity.class)
    private CompanyEntity company;

    @ManyToOne(targetEntity = DriverEntity.class)
    private DriverEntity driver;

    @ManyToOne(targetEntity = DriverEntity.class)
    private DriverEntity coDriver;

    @ManyToOne(targetEntity = TruckEntity.class)
    private TruckEntity truck;

    @Column(length = 500)
    private String description;

    @ManyToOne(targetEntity = StateEntity.class)
    private StateEntity state;

    @Column(length = 100)
    private String city;

    @Column(length = 500)
    private String location;
    @Column
    private boolean violationDiscovered;

    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ACTIVE;

    @Column
    Integer inspectionLevel;

    @Column
    Integer totalFineAmount;

    @Column
    Boolean outOfService;

    @Column
    LocalDate deadlineAt;

    @OneToMany(mappedBy = "inspection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InspectionFile> files = new HashSet<>();

    @OneToMany(mappedBy = "inspection", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ViolationTypes> violationTypes = new ArrayList<>();


    public Inspection(String inspectionNumber,
                      LocalDate inspectionDate,
                      CompanyEntity company,
                      DriverEntity driver,
                      DriverEntity coDriver,
                      TruckEntity truck,
                      String description,
                      StateEntity state,
                      String city,
                      String location,
                      boolean violationDiscovered) {
        this.inspectionNumber = inspectionNumber;
        this.inspectionDate = inspectionDate;
        this.company = company;
        this.driver = driver;
        this.coDriver = coDriver;
        this.truck = truck;
        this.description = description;
        this.state = state;
        this.city = city;
        this.location = location;
        this.violationDiscovered = violationDiscovered;
    }

}
