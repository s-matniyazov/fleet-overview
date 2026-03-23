package fleetoverview.domain.projection.inspection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolationTypesDto {
    private Integer id;
    private String sectionViolCode;
    private String type;
    private String description;

    public ViolationTypesDto(Integer id, String sectionViolCode, String type, String description) {
        this.id = id;
        this.description = description;
        this.sectionViolCode = sectionViolCode;
        this.type = type;
    }
}
