package fleetoverview.domain.entity.inspection;

import fleetoverview.domain.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="inspection_levels_ref")
public class InspectionLevelsRef extends BaseEntity {
    @Column
    private Integer level;
    @Column(length = 300)
    private String description;
}
