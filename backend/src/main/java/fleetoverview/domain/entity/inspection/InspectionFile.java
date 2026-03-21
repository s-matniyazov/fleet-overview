package fleetoverview.domain.entity.inspection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.enums.inspection.InspectionFileTypeEnum;
import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inspection_files", indexes = {@Index(name = "idx_inspection_files_lookup",
        columnList = "inspection_id, type, status" )} )
public class InspectionFile extends BaseEntity {
    @OneToOne
    private ResourceEntity resource;

    @Enumerated(EnumType.STRING)
    private InspectionFileTypeEnum type;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(length = 2000)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Inspection inspection;

    public InspectionFile(ResourceEntity resource, InspectionFileTypeEnum type, StatusEnum status, Inspection inspection) {
        this.resource = resource;
        this.type = type;
        this.status = status;
        this.inspection = inspection;
    }
}
