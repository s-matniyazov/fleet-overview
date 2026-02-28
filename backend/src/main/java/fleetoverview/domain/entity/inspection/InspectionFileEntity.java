package fleetoverview.domain.entity.inspection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fleetoverview.domain.entity.ResourceEntity;
import fleetoverview.domain.entity.base.BaseEntity;
import fleetoverview.domain.enums.StatusEnum;
import fleetoverview.domain.enums.inspection.InspectionFileTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inspection_files")
public class InspectionFileEntity extends BaseEntity {
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
    private InspectionEntity inspection;

}
