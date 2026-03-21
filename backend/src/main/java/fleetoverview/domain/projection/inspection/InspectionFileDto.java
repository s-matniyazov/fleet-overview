package fleetoverview.domain.projection.inspection;

import fleetoverview.domain.entity.ResourceEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionFileDto {
    private Integer id;
    private String type;
    private String status;
    private String description;
    private String fileName;
    private Long size;
    private ResourceEntity resource;

    public InspectionFileDto(Integer id,
                             ResourceEntity resource,
                             String type,
                             String status,
                             String description,
                             String fileName,
                             Long size)
    {
        this.id = id;
        this.resource = resource;
        this.type = type;
        this.status = status;
        this.description = description;
        this.fileName = fileName;
        this.size = size;
    }

    public InspectionFileDto(Integer id,
                             String type,
                             String status,
                             String description,
                             String fileName,
                             Long size)
    {
        this.id = id;
        this.type = type;
        this.status = status;
        this.description = description;
        this.fileName = fileName;
        this.size = size;
    }

}
