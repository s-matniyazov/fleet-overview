package fleetoverview.domain.projection.inspection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionLevelDto {
    private Integer id;
    private Integer level;
    private String description;

    public InspectionLevelDto(Integer id, Integer level,String description) {
        this.id = id;
        this.level = level;
        this.description = description;
    }
}
